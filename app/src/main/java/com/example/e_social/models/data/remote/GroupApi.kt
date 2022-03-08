package com.example.e_social.models.data.remote

import com.example.e_social.models.data.response.PostListResponse
import retrofit2.Response
import retrofit2.http.GET

interface GroupApi {
    @GET
    suspend fun getGroups():Response<PostListResponse>
}