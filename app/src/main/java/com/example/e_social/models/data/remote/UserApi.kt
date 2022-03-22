package com.example.e_social.models.data.remote

import com.example.e_social.models.data.request.*
import com.example.e_social.models.data.response.*
import com.example.e_social.models.domain.model.UserModel
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @POST("login")
    suspend fun login(@Body user: LoginRequest):Response<LoginResponse>

    @POST("signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest):Response<SignUpResponse>

    @GET("user/info")
    suspend fun getUserInfo(): Response<UserInfo>

    @GET("user/info/{id}")
    suspend fun getUserInfo(@Path("id") id:String): Response<UserInfo>

    @PUT("user/{id}/edit")
    suspend fun editAccount(@Path("id") id:String, @Body editAccountRequest: EditAccountRequest):Response<EditAccountResponse>

    @POST("deposit")
    suspend fun depositCoins(@Body depositCoinsRequest: DepositCoinsRequest):Response<MomoResponse>

    @POST("withdraw")
    suspend fun withdrawCoins(@Body withdrawCoinsResponse: WithdrawCoinsRequest):Response<WithdrawCoinsResponse>
}