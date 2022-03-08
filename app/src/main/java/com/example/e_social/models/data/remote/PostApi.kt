package com.example.e_social.models.data.remote

import com.example.e_social.models.data.response.PostListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApi {
    @GET("posts")
    suspend fun getPosts(@Query("limit")limit:Int,@Query("offset")offset:Int):Response<PostListResponse>

}