package com.example.e_social.models.data.repo.post.impl

import android.util.Log
import com.example.e_social.models.data.remote.PostApi
import com.example.e_social.models.data.repo.post.PostRepository
import com.example.e_social.models.data.response.PostListResponse
import com.example.e_social.models.data.response.VoteResponse
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.ErrorUtils
import com.example.e_social.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.NullPointerException
import java.net.HttpURLConnection
import javax.inject.Inject

@ActivityScoped
class PostRepositoryImpl @Inject constructor(private val api: PostApi):PostRepository  {

    override suspend fun getPosts(limit: Int, offset: Int): Resource<PostListResponse> {
        val response = api.getPosts(limit=limit, offset = offset)
        return when(response.code()){
            HttpURLConnection.HTTP_BAD_METHOD -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_NOT_FOUND -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))            }
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))            }
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

    override suspend fun voteUp(postId: String): Resource<VoteResponse> {
        val response = api.voteUp(postId = postId,up = "true")
        Log.d("Voteup", response.body()?.votes.toString())
        return when(response.code()){
            HttpURLConnection.HTTP_BAD_METHOD -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))            }
            HttpURLConnection.HTTP_NOT_FOUND -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))            }
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))            }
            HttpURLConnection.HTTP_FORBIDDEN -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))            }
            else -> {
                response.body()?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error("Empty response")
            }
        }
    }

    override suspend fun voteDown(postId: String): Resource<VoteResponse> {
        val response = api.voteUp(postId = postId, down = "true")
        return when(response.code()){
            HttpURLConnection.HTTP_BAD_METHOD -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))            }
            HttpURLConnection.HTTP_NOT_FOUND -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))            }
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))            }
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