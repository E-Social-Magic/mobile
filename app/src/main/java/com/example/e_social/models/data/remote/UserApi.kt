package com.example.e_social.models.data.remote

import com.example.e_social.models.data.request.SignUpRequest
import com.example.e_social.models.data.request.LoginRequest
import com.example.e_social.models.data.response.LoginResponse
import com.example.e_social.models.data.response.SignUpResponse
import com.example.e_social.models.domain.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("login")
    suspend fun login(@Body user: LoginRequest):Response<LoginResponse>

    @POST("signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest):Response<SignUpResponse>

    @GET("user/info")
    suspend fun getUserInfo(@Path("id") id:String): Response<UserModel>



}