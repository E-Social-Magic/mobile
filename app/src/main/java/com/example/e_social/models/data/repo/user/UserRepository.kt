package com.example.e_social.models.data.repo.user

import com.example.e_social.models.data.request.LoginRequest
import com.example.e_social.models.data.request.SignUpRequest
import com.example.e_social.models.data.response.LoginResponse
import com.example.e_social.models.data.response.SignUpResponse
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.Resource

interface UserRepository {
    suspend fun getUserInfo(): Resource<UserModel>
    suspend fun login(userModelRequest: LoginRequest): Resource<LoginResponse>
    suspend fun signUp(signUpRequest: SignUpRequest): Resource<SignUpResponse>
}