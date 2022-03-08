package com.example.e_social.models.data.repo.post

import com.example.e_social.models.data.response.PostListResponse
import com.example.e_social.util.Resource

interface PostRepository {
    suspend fun getPosts(limit:Int,offset:Int): Resource<PostListResponse>
}