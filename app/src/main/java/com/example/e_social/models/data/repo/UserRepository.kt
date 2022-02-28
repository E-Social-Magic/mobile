package com.example.e_social.models.data.repo

import com.example.e_social.models.data.remote.UserAPI
import com.example.e_social.models.data.request.LoginRequest
import com.example.e_social.models.data.request.SignUpRequest
import com.example.e_social.models.data.response.LoginResponse
import com.example.e_social.models.data.response.SignUpResponse
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

@ActivityScoped
class UserRepository @Inject constructor(private val api: UserAPI) {

    suspend fun getUserInfo(): Resource<UserModel> {
        val response = try {
            api.getUserInfo("passihere")
        } catch (e: Exception) {
            return Resource.Error(" an error occurred")
        }
        return Resource.Success(response)
    }

    suspend fun login(userModelRequest: LoginRequest): Resource<LoginResponse> {
        val response = try {
            api.login(userModelRequest)
        } catch (e: HttpException) {
            when (e.code()) {
                HttpURLConnection.HTTP_BAD_METHOD -> {
                    return Resource.Error("Vui long nhap thong tin")
                }
                HttpURLConnection.HTTP_NOT_FOUND -> {
                    return Resource.Error("Not found")
                }
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    return Resource.Error("Unauthorized")
                }
                HttpURLConnection.HTTP_FORBIDDEN -> {
                    return Resource.Error("No permission")
                }
            }
            return Resource.Error("an error occurred")
        }
        return when (response.success) {
            true -> {
                Resource.Success(response)
            }
            false -> {
                Resource.Error(response.msg)
            }
        }
    }

    suspend fun signUp(signUpRequest: SignUpRequest): Resource<SignUpResponse> {
        val res = try {
            api.signUp(signUpRequest = signUpRequest)
        } catch (e: HttpException) {
            when (e.code()) {
                HttpURLConnection.HTTP_BAD_METHOD -> {
                    return Resource.Error("Vui long nhap thong tin")
                }
                HttpURLConnection.HTTP_NOT_FOUND -> {
                    return Resource.Error("Not found")
                }
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    return Resource.Error("Unauthorized")
                }
                HttpURLConnection.HTTP_FORBIDDEN -> {
                    return Resource.Error("No permission")
                }
            }
            return Resource.Error("an error occurred")
        }

        return Resource.Success(res)
    }




}


