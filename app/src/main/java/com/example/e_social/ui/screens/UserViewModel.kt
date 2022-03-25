package com.example.e_social.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_social.models.data.repo.user.UserRepository
import com.example.e_social.models.data.repo.user.impl.UserRepositoryImpl
import com.example.e_social.models.data.response.UserInfo
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel(){
    private  val _user= MutableLiveData<UserInfo>()
    val user = _user
    var searchBarState = mutableStateOf(false)
    var searchValue = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    fun onSearchBarStateChange(newValue:Boolean){
        searchBarState.value=newValue
    }

    fun onSearchChange(newValue: String){
        searchValue.value = newValue
    }
    fun getUserInfo(){
        viewModelScope.launch {
            isLoading.value=true
            val result =userRepository.getUserInfo()
            when(result){
                is Resource.Success -> {
                    if (result.data != null)
                        _user.value=result.data.user
                }
                else ->{

                }
            }
            isLoading.value=false
        }
    }

}