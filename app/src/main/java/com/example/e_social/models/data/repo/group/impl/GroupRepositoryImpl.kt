package com.example.e_social.models.data.repo.group.impl

import com.example.e_social.models.data.remote.GroupApi
import com.example.e_social.models.data.repo.group.GroupRepository
import com.example.e_social.models.data.response.Topic
import com.example.e_social.models.data.response.TopicList
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.ErrorUtils
import com.example.e_social.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.net.HttpURLConnection
import javax.inject.Inject

@ActivityScoped
class GroupRepositoryImpl @Inject constructor(private val api: GroupApi):GroupRepository {

    override suspend fun getGroups(): Resource<TopicList> {
        val response = api.getGroups()
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

    override suspend fun getGroups(userId:String): Resource<TopicList> {
        val response = api.getGroups(userId)
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
}