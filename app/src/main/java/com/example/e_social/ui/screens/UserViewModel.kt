package com.example.e_social.ui.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_social.models.data.repo.user.impl.UserRepositoryImpl
import com.example.e_social.models.domain.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepositoryImpl):ViewModel(){
    private  val _user= MutableLiveData<UserModel>()
    val user = _user


}