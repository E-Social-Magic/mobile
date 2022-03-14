package com.example.e_social.models.data.remote

import com.example.e_social.models.data.response.PostListResponse
import com.example.e_social.models.data.response.VoteResponse
import retrofit2.Response
import retrofit2.http.*

interface PostApi {
    @GET("posts")
    suspend fun getPosts(@Query("limit")limit:Int,@Query("offset")offset:Int):Response<PostListResponse>
    @GET("posts/{post-id}/vote")
    suspend fun voteUp(@Path("post-id")postId:String, @Query("up")up:String="false", @Query("down")down:String= "false"):Response<VoteResponse>

    @POST("post/new")
    suspend fun post(@Header("authorization") token:String)

}