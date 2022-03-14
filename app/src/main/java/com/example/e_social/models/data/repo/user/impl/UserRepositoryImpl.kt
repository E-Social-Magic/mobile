package com.example.e_social.models.data.repo.user.impl

import android.util.Log
import com.example.e_social.models.data.remote.UserApi
import com.example.e_social.models.data.repo.user.UserRepository
import com.example.e_social.models.data.request.LoginRequest
import com.example.e_social.models.data.request.SignUpRequest
import com.example.e_social.models.data.response.LoginResponse
import com.example.e_social.models.data.response.SignUpResponse
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.ErrorUtils
import com.example.e_social.util.Resource
import com.example.e_social.util.SessionManager
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.NullPointerException
import java.net.HttpURLConnection
import javax.inject.Inject

@ActivityScoped
class UserRepositoryImpl @Inject constructor(private val api: UserApi) : UserRepository {

    override suspend fun getUserInfo(): Resource<UserModel> {
        val response = api.getUserInfo("passihere")
        return when (response.code()) {
            HttpURLConnection.HTTP_BAD_REQUEST -> {
                val errorBody = response.errorBody()?:run{
                    return Resource.Error(message = "Unknown error")
                }

                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_NOT_FOUND -> {
                val errorBody = response.errorBody()?:run{
                    return Resource.Error(message = "Unknown error")
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                val errorBody = response.errorBody()?:run{
                    return Resource.Error(message = "Unknown error")
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_FORBIDDEN -> {
                val errorBody = response.errorBody()?:run{
                    return Resource.Error(message = "Unknown error")
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            else -> {
                response.body()?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error("Empty response")
            }
        }
    }

    override suspend fun login(userModelRequest: LoginRequest): Resource<LoginResponse> {

        val response = api.login(userModelRequest)
        return when (response.code()) {
            HttpURLConnection.HTTP_BAD_REQUEST -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_NOT_FOUND -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_FORBIDDEN -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            else -> {
                response.body()?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error("Empty response")
            }
        }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): Resource<SignUpResponse> {
        val response = api.signUp(signUpRequest = signUpRequest)
        return when (response.code()) {
            HttpURLConnection.HTTP_BAD_REQUEST -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_NOT_FOUND -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            HttpURLConnection.HTTP_FORBIDDEN -> {
                val errorBody = response.errorBody()?:run{
                    throw NullPointerException()
                }
                return  Resource.Error(data = ErrorUtils.parseError(errorBody = errorBody))
            }
            else -> {
                response.body()?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error("Empty response")
            }
        }
    }
}


