package com.example.e_social.models.data.repo.user

import com.example.e_social.models.data.request.*
import com.example.e_social.models.data.response.*
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.Resource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UserRepository {
    suspend fun getUserInfo(): Resource<UserResponse>
    suspend fun getUserInfo(id:String): Resource<UserResponse>
    suspend fun login(userModelRequest: LoginRequest): Resource<LoginResponse>
    suspend fun signUp(signUpRequest: SignUpRequest): Resource<SignUpResponse>
    suspend fun editAccount(id:String, editAccountRequest: EditAccountRequest): Resource<EditAccountResponse>
    suspend fun depositCoins(depositCoinsRequest: DepositCoinsRequest): Resource<MomoResponse>
    suspend fun withdrawCoins(withdrawCoinsResponse: WithdrawCoinsRequest): Resource<WithdrawCoinsResponse>
    suspend fun joinGroups(listGroupId:List<String>):Resource<JoinGroupResponse>
}