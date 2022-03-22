package com.example.e_social.models.data.repo.post.impl

import android.util.Log
import android.webkit.MimeTypeMap
import com.example.e_social.models.data.remote.PostApi
import com.example.e_social.models.data.repo.post.PostRepository
import com.example.e_social.models.data.request.CommentRequest
import com.example.e_social.models.data.request.NewPostRequest
import com.example.e_social.models.data.response.NewCommentResponse
import com.example.e_social.models.data.response.PostListResponse
import com.example.e_social.models.data.response.PostResponse
import com.example.e_social.models.data.response.VoteResponse
import com.example.e_social.models.domain.model.PostModel
import com.example.e_social.util.ErrorUtils
import com.example.e_social.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Part
import java.io.File
import java.net.HttpURLConnection
import javax.inject.Inject


@ActivityScoped
class PostRepositoryImpl @Inject constructor(private val api: PostApi) : PostRepository {

    override suspend fun getPosts(limit: Int, offset: Int): Resource<PostListResponse> {
        val response = api.getPosts(limit = limit, offset = offset)
        return when (response.code()) {
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

    override suspend fun voteUp(postId: String): Resource<VoteResponse> {
        val response = api.voteUp(postId = postId, up = "true")
        return when (response.code()) {
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
                val errorBody = response.errorBody() ?: run {
                    throw NullPointerException()
                }
                return Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            else -> {
                response.body()?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error("Empty response")
            }
        }
    }

    override suspend fun voteDown(postId: String): Resource<VoteResponse> {
        val response = api.voteUp(postId = postId, down = "true")
        return when (response.code()) {
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

    override suspend fun newPost(postModel: PostModel): Resource<PostResponse> {
        val map: HashMap<String, RequestBody> = HashMap()
        val newPostRequest = NewPostRequest(
            title = postModel.title.toRequestBody(MultipartBody.FORM),
            content = postModel.content.toRequestBody(MultipartBody.FORM),
            hideName = postModel.hideName.toString().toRequestBody(MultipartBody.FORM),
            expired = postModel.expired.toString().toRequestBody(MultipartBody.FORM),
            coins = postModel.coins.toString().toRequestBody(MultipartBody.FORM),
            costs = postModel.costs.toString().toRequestBody(MultipartBody.FORM))

        map["title"] =  "newPostRequest.title".toRequestBody(MultipartBody.FORM)
        map["content"] = "newPostRequest.content".toRequestBody(MultipartBody.FORM)
        map["hideName"] = newPostRequest.hideName
        map["costs"] = newPostRequest.costs
        map["expired"] = newPostRequest.expired
        map["coins"] = newPostRequest.coins
        val uploads = listFiletoMultiBody(postModel.files)

        val response = api.newPost(params = map, files = uploads)
        return when (response.code()) {
            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                val errorBody = response.errorBody() ?: run {
                    throw NullPointerException()
                }
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
                Log.d("File", ErrorUtils.parseError<String>(errorBody = errorBody).toString())

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
                Resource.Error(data = response.body()?.post, message = "HTTP_FORBIDDEN")
            }
            else -> {
                response.body()?.let {
                    Resource.Success(data = it.post)
                } ?: Resource.Error("Empty response")
            }
        }
    }

    override suspend fun newComment(
        postId: String,
        comment: CommentRequest,
        files: List<File>?
    ): Resource<NewCommentResponse> {


        val map: HashMap<String, RequestBody> = HashMap()
        map["comment"] = comment.comment
        val response = api.newComment(postId = postId, params = map, files = listFiletoMultiBody(files))
        return when (response.code()) {
            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                val errorBody = response.errorBody() ?: run {
                    throw NullPointerException()
                }
                Log.d("File", ErrorUtils.parseError<String>(errorBody = errorBody).toString())

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

    fun listFiletoMultiBody(files: List<File>?): List<MultipartBody.Part>? {
        if (files == null) {
            return null
        }
        return files.map { file ->
            MultipartBody.Part.createFormData(
                name = "files", filename = file.name, file.asRequestBody(
                    MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension)
                        ?.toMediaTypeOrNull()
                )
            )
        }
    }
}