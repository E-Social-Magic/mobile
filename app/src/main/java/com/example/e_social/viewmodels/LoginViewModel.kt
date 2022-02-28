package com.example.e_social.viewmodels

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_social.models.data.remote.UserService
import com.example.e_social.models.domain.model.UserModel
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel(){
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password
    private  val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user
    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    fun onEmailChange(newEmail:String){
        _email.value= newEmail
        Log.d("Login View Model",newEmail)
    }
    fun onPasswordChange(newPassword:String){
        _password.value= newPassword
    }
    fun  getUserInfo(){
    }
    fun login(){
        viewModelScope.launch {
            val apiService = UserService.getInstance()
            try {
                val userService = apiService.login()
                _user.value = userService
            }
            catch (e: Exception) {
                _errorMessage.value = e.message.toString()
            }
        }
    }
    fun isLogin(): Boolean {
        return true;
    }
}
