package com.example.e_social.ui.screens.featurePost

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_social.models.Constants
import com.example.e_social.models.data.repo.post.PostRepository
import com.example.e_social.models.domain.model.PostEntry
import com.example.e_social.models.domain.model.PostListEntry
import com.example.e_social.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository):ViewModel() {
    private var currentPage =1
    var postList = mutableStateOf<List<PostEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)
    init {
        loadPostPaginated()

    }
    fun loadPostPaginated(){

        viewModelScope.launch{
            isLoading.value=true
            val result = postRepository.getPosts(Constants.POST_SIZE,currentPage)
            when (result){
                is Resource.Success -> {
                    endReached.value = currentPage >=result.data!!.totalPages
                    val postListEntry = result.data.posts.mapIndexed{ index, entry ->
                        PostEntry(entry.title,entry.content,images=entry.images,entry.userId,entry.visible, createdAt = entry.createdAt,updatedAt=entry.updatedAt)
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

}