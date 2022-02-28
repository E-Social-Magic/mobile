package com.example.e_social.models.data.repo.implement

import androidx.lifecycle.LiveData
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.models.data.repo.User

class UserImpl: User{
    private val isLogin:Boolean = false
    override fun getUserInfo(): LiveData<UserModel> {
//        return Result.Loading<Nothing>;
        TODO("Not yet implemented")
    }

    override fun login(): LiveData<UserModel> {
        TODO("Not yet implemented")
    }

    override fun signup(): LiveData<UserModel> {
        TODO("Not yet implemented")
    }

    override fun isLogin(): Boolean {
        TODO("Not yet implemented")
    }

}