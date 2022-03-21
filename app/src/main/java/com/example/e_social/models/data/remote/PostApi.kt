package com.example.e_social.models.data.remote

import com.example.e_social.models.data.response.PostListResponse
import com.example.e_social.models.data.response.PostResponse
import com.example.e_social.models.data.response.VoteResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface PostApi {
    @GET("posts")
    suspend fun getPosts(@Query("limit")limit:Int,@Query("offset")offset:Int):Response<PostListResponse>
    @GET("post/{post-id}/vote")
    suspend fun voteUp(@Path("post-id")postId:String, @Query("up")up:String="false", @Query("down")down:String= "false"):Response<VoteResponse>

    @GET("post/{post-id}")
    suspend fun getPostById(@Path("post-id")postId: String):Response<PostResponse>

    @Multipart
    @POST("post/new")
    suspend fun newPost(
        @Part files: List<MultipartBody.Part>?,
        @Part("title") title: RequestBody?,
        @Part("content") content: RequestBody?
    ):Response<PostResponse>

}