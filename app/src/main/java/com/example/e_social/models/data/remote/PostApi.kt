package com.example.e_social.models.data.remote

import com.example.e_social.models.data.response.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApi {
    @GET("groups/{group-id}/posts")
    suspend fun getPosts(@Path("group-id")groupId:String,@Query("offset")offset:Int):Response<PostResponse>
}