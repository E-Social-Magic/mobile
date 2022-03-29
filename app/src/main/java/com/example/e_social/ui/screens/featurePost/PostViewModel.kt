package com.example.e_social.ui.screens.featurePost

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_social.models.Constants
import com.example.e_social.models.data.repo.post.PostRepository
import com.example.e_social.models.data.request.CommentRequest
import com.example.e_social.models.data.response.Comment
import com.example.e_social.models.data.response.PostResponse
import com.example.e_social.models.domain.model.Message
import com.example.e_social.models.domain.model.PostEntry
import com.example.e_social.models.domain.model.PostModel
import com.example.e_social.util.Resource
import com.example.e_social.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.DecimalFormat
import java.text.NumberFormat
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository,private val sessionManager: SessionManager) : ViewModel() {
    private var currentPage = 1
    var postList = mutableStateOf<List<PostEntry>>(listOf())
    var allposts = mutableStateOf<List<PostEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)
    val newPost = mutableStateOf(PostModel(title = "", content = "", files = listOf(), groupId = "", coins = 100))
    val groupId= mutableStateOf("")
    val coins =sessionManager.fetchCoin()
    val userIdSesstion = sessionManager.fetchUserId()
    private var lastScrollIndex = 0
    private val _scrollUp = MutableLiveData(false)
    var searchValue = mutableStateOf("")
    var searchBarState = mutableStateOf(false)

    fun onSearchBarStateChange(newValue:Boolean){
        searchBarState.value = newValue
    }


    fun onSearchChange(newValue: String){
        viewModelScope.launch {
            searchValue.value = newValue
            if (searchValue.value.isEmpty()) {
                postList.value = allposts.value
                return@launch
            }
            delay(1000)
            val postsFormSearch = allposts.value.filter { data ->
                data.title.contains(searchValue.value, true) || data.content.contains(searchValue.value, true)
            }

            postList.value = postsFormSearch
        }
    }
    val scrollUp: LiveData<Boolean>
        get() = _scrollUp

    init {
        loadPostPaginated()
    }
    fun refresh(groupById: String?){
        postList.value =listOf()
        currentPage = 1
        loadPostPaginated(groupById)
    }
    fun loadPostPaginated(groupById: String?=null) {
        viewModelScope.launch {
            isLoading.value = true
            val searchBy= groupById ?: if (groupId.value.isEmpty()) null else groupId.value
            val result = postRepository.getPosts(Constants.POST_SIZE, currentPage,searchBy)
            when (result) {
                is Resource.Success -> {
                    endReached.value = currentPage >= result.data!!.totalPages
                    val postListEntry = result.data.posts.filter { !postList.value.map {value -> value.id}.contains(it.id)  }.mapIndexed { index, entry ->
                        postResponse2PostEntry(postReponse = entry)
                    }
                    currentPage++
                    loadError.value = ""
                    isLoading.value = false
                    postList.value += postListEntry
                    allposts.value = postList.value
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                else -> {
                    isLoading.value = false
                }
            }
        }
    }

    fun voteUp(postId: String) {
        viewModelScope.launch {
            var votes = 0
            isLoading.value = true
            val result = postRepository.voteUp(postId)
            when (result) {
                is Resource.Success -> {
                    votes = result.data?.votes!!
                }
                is Resource.Error -> {
                    votes = result.data?.votes!!
                }
                else -> {

                }
            }
            val replacement =
                postList.value.map { if (it.id == postId) it.copy(votes = votes) else it }
            postList.value = replacement
            isLoading.value = false
        }


    }

    fun voteDown(postId: String) {
        var votes = 0
        viewModelScope.launch {
            isLoading.value = true
            val result = postRepository.voteDown(postId)
            when (result) {
                is Resource.Success -> {
                    votes = result.data?.votes ?: 0
                }
                is Resource.Error -> {
                    votes = result.data?.votes ?: 0
                    isLoading.value = false
                }
                else -> {

                }
            }
            val replacement =
                postList.value.map { if (it.id == postId) it.copy(votes = votes) else it }
            postList.value = replacement
            isLoading.value = false
        }
    }

    fun onTitleChange(title: String) {
        newPost.value = newPost.value.copy(title = title)
    }

    fun onContentChange(content: String) {
        newPost.value = newPost.value.copy(content = content)
    }
    fun  onGroupSelected(groupId: String){
        newPost.value= newPost.value.copy(groupId=groupId)
    }
    fun createPost() {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.newPost(postModel = newPost.value)
            loadPostPaginated()
        }
    }
    fun markAnswerIsCorrect(postId: String,commentId: String){
        viewModelScope.launch {
            postRepository.markAnswerIsCorrect(postId = postId, commentId = commentId)
        }
    }
    fun loadPostByGroup(groupId:String){
        viewModelScope.launch {
            isLoading.value = true
            val result = postRepository.getPosts(Constants.POST_SIZE, currentPage)
            when (result) {
                is Resource.Success -> {
                    endReached.value = currentPage >= result.data!!.totalPages
                    val postListEntry = result.data.posts.mapIndexed { index, entry ->
                        postResponse2PostEntry(postReponse = entry)
                    }
                    currentPage++
                    loadError.value = ""
                    isLoading.value = false
                    postList.value += postListEntry
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                else -> {}
            }
        }
    }
    suspend fun findPostById(id: String): PostEntry? {
        var post: PostEntry? = null
            isLoading.value = true
            val result = postRepository.getPostById(id)
            try {
                when (result) {
                    is Resource.Success -> {
                        if (result.data!=null)
                        post = postResponse2PostEntry(result.data)
                    }
                    is Resource.Error -> {
                        post = null
                    }
                    else -> {
                        post = null
                    }
                }
            } catch (e: Exception) {
                post = null
            }
            isLoading.value = false
        return post
    }

    fun submitComment(postId: String, message: Message): Boolean {
        viewModelScope.launch{
            isLoading.value = true
            val result = postRepository.newComment(postId = postId, comment = CommentRequest(
                message.message.toRequestBody(
                    MultipartBody.FORM
                )
            ), files = message.images?.map { File(it) })
            try {
                when (result) {
                    is Resource.Success -> {
                        val post = postResponse2PostEntry(result.data!!.post)
                        val replacement =
                            postList.value.map { if (it.id == postId) it.copy(comments = post.comments) else it }
                        postList.value = replacement
                    }
                    is Resource.Error -> {

                    }
                    else -> {

                    }
                }
            } catch (e: Exception) {

            }

            isLoading.value = false
        }
        return true
    }


    companion object{
        fun commentToMessage(comments: List<Comment>): List<Message> {
            return comments.map{
                Message(
                    id =it._id,
                    authorName = it.userName,
                    avatarAuthor = it.avatar,
                    message = it.comment,
                    images = it.images,
                    isCorrect = it.correct,
                    userId = it.userId
                )
            }.reversed()
        }
        fun postResponse2PostEntry(postReponse: PostResponse): PostEntry {
            return PostEntry(
                title = postReponse.title,
                createdAt = postReponse.createdAt,
                images = postReponse.images,
                comments = commentToMessage(postReponse.comments),
                authorAvatar = postReponse.authorAvatar,
                userName = postReponse.userName,
                id = postReponse.id,
                content = postReponse.content,
                votes = postReponse.votes,
                updatedAt = postReponse.updatedAt,
                userId = postReponse.userId,
                videos = postReponse.videos,
                hideName = postReponse.hideName,
                expired = postReponse.expired,
                coins = postReponse.coins,
                costs = postReponse.costs
            )
        }
    }


    fun onPostChange(newPostModel: PostModel) {
        newPost.value = newPostModel
    }
    fun updateScrollPosition(newScrollIndex: Int) {
        if (newScrollIndex == lastScrollIndex) return
        _scrollUp.value = newScrollIndex > lastScrollIndex
        lastScrollIndex = newScrollIndex
    }
    fun  onCostSelected (costSelected :Boolean){
        newPost.value= newPost.value.copy(costs = costSelected)
    }
    fun  onHideNameSelected(hideNameValue:Boolean){
        newPost.value= newPost.value.copy(hideName = hideNameValue)
    }
    fun onExpiredChange(newExpried:Long){
        newPost.value= newPost.value.copy(expired = newExpried)
    }
    fun  onCoinChange(newCoins:Int){
        newPost.value = newPost.value.copy(coins = newCoins)
    }
}