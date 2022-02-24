package com.example.e_social.models.repo

import androidx.lifecycle.LiveData
import com.example.e_social.models.domain.model.UserModel

interface User{
    fun getUserInfo():LiveData<UserModel>
    fun login():LiveData<UserModel>
    fun signup():LiveData<UserModel>
    fun isLogin():Boolean

//    fun getUserGroup():LiveData<>
}
