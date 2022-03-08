package com.example.e_social.models.data.repo.group

import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.Resource

interface GroupRepository {
    suspend fun getUserInfo(): Resource<UserModel>

}