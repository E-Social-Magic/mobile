package com.example.e_social.models.data.repo.post.impl

import android.util.Log
import com.example.e_social.models.data.remote.PostApi
import com.example.e_social.models.data.repo.post.PostRepository
import com.example.e_social.models.data.response.PostListResponse
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.net.HttpURLConnection
import javax.inject.Inject

@ActivityScoped
class PostRepositoryImpl @Inject constructor(private val api: PostApi):PostRepository  {

    override suspend fun getPosts(limit: Int, offset: Int): Resource<PostListResponse> {
        val response = api.getPosts(limit=limit, offset = offset)
        Log.d("Debug",response.toString())
        return when(response.code()){
            HttpURLConnection.HTTP_BAD_METHOD -> {
                Resource.Error(data = response.body(), message = "HTTP_BAD_METHOD")
            }
            HttpURLConnection.HTTP_NOT_FOUND -> {
                Resource.Error(data = response.body(), message = "HTTP_NOT_FOUND")
            }
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                Resource.Error(data = response.body(), message = "HTTP_UNAUTHORIZED")
            }
            HttpURLConnection.HTTP_FORBIDDEN -> {
                Resource.Error(data = response.body(), message = "HTTP_FORBIDDEN")
            }
            else -> {
                response.body()?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error("Empty response")
            }
        }


    }

}