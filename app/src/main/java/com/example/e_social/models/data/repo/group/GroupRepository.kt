package com.example.e_social.models.data.repo.group

import com.example.e_social.models.data.response.Topic
import com.example.e_social.models.data.response.TopicList
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.Resource

interface GroupRepository {
    suspend fun getGroups(): Resource<TopicList>
    suspend fun getGroups(userId:String): Resource<TopicList>

}