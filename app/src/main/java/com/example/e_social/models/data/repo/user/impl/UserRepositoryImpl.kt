package com.example.e_social.models.data.repo.user.impl

import com.example.e_social.models.data.remote.UserApi
import com.example.e_social.models.data.repo.user.UserRepository
import com.example.e_social.models.data.request.LoginRequest
import com.example.e_social.models.data.request.SignUpRequest
import com.example.e_social.models.data.response.LoginResponse
import com.example.e_social.models.data.response.SignUpResponse
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.net.HttpURLConnection
import javax.inject.Inject

@ActivityScoped
class UserRepositoryImpl @Inject constructor(private val api: UserApi) : UserRepository {

    override suspend fun getUserInfo(): Resource<UserModel> {
        val response = api.getUserInfo("passihere")
        return when (response.code()) {
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

    override suspend fun login(userModelRequest: LoginRequest): Resource<LoginResponse> {

        val response = api.login(userModelRequest)
        return when (response.code()) {
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

    override suspend fun signUp(signUpRequest: SignUpRequest): Resource<SignUpResponse> {
        val response = api.signUp(signUpRequest = signUpRequest)
        return when (response.code()) {
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


