package com.example.e_social.ui.screens.featurePost

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_social.models.Constants
import com.example.e_social.models.data.repo.post.PostRepository
import com.example.e_social.models.domain.model.PostEntry
import com.example.e_social.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {
    private var currentPage = 1
    var postList = mutableStateOf<List<PostEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadPostPaginated()
    }

    fun loadPostPaginated() {

        viewModelScope.launch {
            isLoading.value = true
            val result = postRepository.getPosts(Constants.POST_SIZE, currentPage)
            when (result) {
                is Resource.Success -> {
                    endReached.value = currentPage >= result.data!!.totalPages
                    val postListEntry = result.data.posts.mapIndexed { index, entry ->
                        PostEntry(
                            id = entry.id,
                            entry.title,
                            entry.content,
                            images = entry.images,
                            userId = entry.userId,
                            visible = entry.visible,
                            createdAt = entry.createdAt,
                            updatedAt = entry.updatedAt,
                            votes = entry.votes,
                            videos = entry.videos
                        )
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
            }
        }
    }

    fun voteUp(postId: String): Int {
        var votes = 0
        viewModelScope.launch {
            isLoading.value = true
            val result = postRepository.voteUp(postId)
            when (result) {
                is Resource.Success -> {
                    votes = result.data?.votes ?: 0
                }
                is Resource.Error -> {
                    votes = result.data?.votes ?: 0
                    isLoading.value = false
                }
            }
        }
        return votes
    }

    fun voteDown(postId: String): Int {
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
            }
        }
        return votes
    }

}