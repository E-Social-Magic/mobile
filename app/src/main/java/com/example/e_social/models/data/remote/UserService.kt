package com.example.e_social.models.data.remote

import com.example.e_social.models.domain.model.UserModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("login")
    suspend fun login():UserModel
    companion object {
        var userService: UserService? = null
        fun getInstance() : UserService {
            if (userService == null) {
                userService = Retrofit.Builder()
                    .baseUrl("https://howtodoandroid.com/apis/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(UserService::class.java)
            }
            return userService!!
        }
    }
}