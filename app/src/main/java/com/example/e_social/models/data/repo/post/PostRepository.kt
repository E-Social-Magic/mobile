package com.example.e_social.models.data.repo.post

import com.example.e_social.models.data.request.CommentRequest
import com.example.e_social.models.data.request.NewPostRequest
import com.example.e_social.models.data.response.*
import com.example.e_social.models.domain.model.PostModel
import com.example.e_social.util.Resource
import java.io.File

interface PostRepository {
    suspend fun getPosts(limit:Int,offset:Int,groupId:String?=null): Resource<PostListResponse>
    suspend fun voteUp(postId:String):Resource<VoteResponse>
    suspend fun voteDown(postId:String):Resource<VoteResponse>
    suspend fun voteUp(postId:String,commentId: String):Resource<VoteResponse>
    suspend fun voteDown(postId:String,commentId: String):Resource<VoteResponse>
    suspend fun newPost(postModel: PostModel):Resource<PostResponse>
    suspend fun getPostById(postId:String):Resource<PostResponse>
    suspend fun newComment(postId: String,comment: CommentRequest,files:List<File>?):Resource<NewCommentResponse>
    suspend fun markAnswerIsCorrect(postId: String,commentId:String):Resource<MarkCorrectAnswerResponse>
}