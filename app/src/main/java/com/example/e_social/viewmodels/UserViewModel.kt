package com.example.e_social.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_social.models.data.repo.UserRepository
import com.example.e_social.models.domain.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel(){
    private  val _user= MutableLiveData<UserModel>()
    val user = _user


}