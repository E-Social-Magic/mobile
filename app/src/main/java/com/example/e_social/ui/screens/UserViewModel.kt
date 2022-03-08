package com.example.e_social.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_social.models.data.repo.user.UserRepository
import com.example.e_social.models.data.repo.user.impl.UserRepositoryImpl
import com.example.e_social.models.domain.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel(){
    private  val _user= MutableLiveData<UserModel>()
    val user = _user
    var searchBarState = mutableStateOf(false)
    var searchValue = mutableStateOf("")
    fun onSearchBarStateChange(newValue:Boolean){
        searchBarState.value=newValue
    }

    fun onSearchChange(newValue: String){
        searchValue.value = newValue
    }

}