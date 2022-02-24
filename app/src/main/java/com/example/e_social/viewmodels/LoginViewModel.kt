package com.example.e_social.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel(){
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password
    fun onEmailChange(newEmail:String){
        _email.value= newEmail
    }
    fun onPasswordChange(newPassword:String){
        _password.value= newPassword
    }
    fun  getUserInfo(){

    }
}
