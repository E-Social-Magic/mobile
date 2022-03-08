package com.example.e_social.models.data.repo.group.impl

import com.example.e_social.models.data.remote.GroupApi
import com.example.e_social.models.data.repo.group.GroupRepository
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class GroupRepositoryImpl @Inject constructor(private val api: GroupApi):GroupRepository {
    override suspend fun getUserInfo(): Resource<UserModel> {
        TODO("Not yet implemented")
    }

}