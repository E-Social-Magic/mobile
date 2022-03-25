package com.example.e_social.models.data.remote

import com.example.e_social.models.data.response.PostListResponse
import com.example.e_social.models.data.response.TopicList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupApi {
    @GET("groups")
    suspend fun getGroups():Response<TopicList>

    @GET("groups")
    suspend fun getGroups(@Query("user_id")userId:String):Response<TopicList>
}