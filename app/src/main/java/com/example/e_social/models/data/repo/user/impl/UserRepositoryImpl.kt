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
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

@ActivityScoped
class UserRepositoryImpl @Inject constructor(private val api: UserApi): UserRepository {

    override suspend fun getUserInfo(): Resource<UserModel> {
        val response = try {
            api.getUserInfo("passihere")
        } catch (e: Exception) {
            return Resource.Error(" an error occurred")
        }
        return Resource.Success(response)
    }

    override suspend fun login(userModelRequest: LoginRequest): Resource<LoginResponse> {

        val response =  api.login(userModelRequest)
//        try {
//
//        } catch (e: HttpException) {
//            when (e.code()) {
//                HttpURLConnection.HTTP_BAD_METHOD -> {
//                    return Resource.Error("Vui long nhap thong tin")
//                }
//                HttpURLConnection.HTTP_NOT_FOUND -> {
//                    return Resource.Error("Not found")
//                }
//                HttpURLConnection.HTTP_UNAUTHORIZED -> {
//                    return Resource.Error("Unauthorized")
//                }
//                HttpURLConnection.HTTP_FORBIDDEN -> {
//                    return Resource.Error("No permission")
//                }
//            }
//            return Resource.Error("an error occurred")
//        }
        return when (response.code()) {
            HttpURLConnection.HTTP_BAD_METHOD -> {
                Resource.Error(response.message())
            }
            HttpURLConnection.HTTP_BAD_METHOD -> {
                Resource.Error(response.message())
            }
            else -> {
                response.body()?.let{
                    Resource.Success(data =  it)
                } ?: Resource.Error("Empty response")
            }
        }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): Resource<SignUpResponse> {
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


