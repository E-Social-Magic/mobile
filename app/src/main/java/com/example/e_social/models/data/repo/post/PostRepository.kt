package com.example.e_social.models.data.repo.post

import com.example.e_social.models.data.request.NewPostRequest
import com.example.e_social.models.data.response.PostListResponse
import com.example.e_social.models.data.response.PostResponse
import com.example.e_social.models.data.response.VoteResponse
import com.example.e_social.util.Resource

interface PostRepository {
    suspend fun getPosts(limit:Int,offset:Int): Resource<PostListResponse>
    suspend fun voteUp(postId:String):Resource<VoteResponse>
    suspend fun voteDown(postId:String):Resource<VoteResponse>
    suspend fun newPost(newPostRequest: NewPostRequest):Resource<PostResponse>
    suspend fun getPostById(postId:String):Resource<PostResponse>
}