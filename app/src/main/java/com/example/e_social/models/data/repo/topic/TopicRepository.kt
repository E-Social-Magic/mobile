package com.example.e_social.models.data.repo.topic

import com.example.e_social.models.data.response.Topic
import com.example.e_social.models.data.response.TopicList
import com.example.e_social.util.Resource

interface TopicRepository {
    suspend fun getTopicList(limit:Int,offset: Int): Resource<TopicList>
    suspend fun getTopicInfo(pokemonName: String): Resource<Topic>
}