package com.example.e_social.models.repo.implement

import androidx.lifecycle.LiveData
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.models.repo.Result
import com.example.e_social.models.repo.User

class UserImpl: User{
    override fun getUserInfo(): LiveData<UserModel> {
//        return Result.Loading<Nothing>;
        TODO("Not yet implemented")
    }

}