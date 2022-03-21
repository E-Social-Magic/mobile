package com.example.e_social.models.data.repo.post.impl

import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.ContentResolverCompat
import androidx.core.net.toUri
import com.example.e_social.models.data.remote.PostApi
import com.example.e_social.models.data.repo.post.PostRepository
import com.example.e_social.models.data.request.NewPostRequest
import com.example.e_social.models.data.response.PostListResponse
import com.example.e_social.models.data.response.PostResponse
import com.example.e_social.models.data.response.VoteResponse
import com.example.e_social.util.ErrorUtils
import com.example.e_social.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
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

    override suspend fun newPost(newPostRequest: NewPostRequest): Resource<PostResponse> {
        val title = newPostRequest.title.toRequestBody(MultipartBody.FORM)
        val content = newPostRequest.content.toRequestBody(MultipartBody.FORM)

        val files = newPostRequest.files.map{ file->
            MultipartBody.Part.createFormData(name = "files", filename = file.name,file.asRequestBody(
                MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension)
                    ?.toMediaTypeOrNull()
            ))
        }

        val response = api.newPost(files = files, title = title, content = content)
        Log.d("File",response.body().toString())
        return when (response.code()) {
            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                val errorBody = response.errorBody() ?: run {
                    throw NullPointerException()
                }
                Log.d("File",ErrorUtils.parseError<String>(errorBody=errorBody).toString())

                return Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_BAD_METHOD -> {
                val errorBody = response.errorBody() ?: run {
                    throw NullPointerException()
                }
                return Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_NOT_FOUND -> {
                val errorBody = response.errorBody() ?: run {
                    throw NullPointerException()
                }
                return Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                val errorBody = response.errorBody() ?: run {
                    throw NullPointerException()
                }
                return Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
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

    override suspend fun getPostById(postId: String): Resource<PostResponse> {
        val response = api.getPostById(postId)
        return when (response.code()) {
            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                val errorBody = response.errorBody() ?: run {
                    throw NullPointerException()
                }
                Log.d("File",ErrorUtils.parseError<String>(errorBody=errorBody).toString())

                return Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_BAD_METHOD -> {
                val errorBody = response.errorBody() ?: run {
                    throw NullPointerException()
                }
                return Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_NOT_FOUND -> {
                val errorBody = response.errorBody() ?: run {
                    throw NullPointerException()
                }
                return Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                val errorBody = response.errorBody() ?: run {
                    throw NullPointerException()
                }
                return Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
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