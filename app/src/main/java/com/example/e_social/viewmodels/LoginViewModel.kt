package com.example.e_social.viewmodels

//import androidx.hilt.lifecycle.ViewModelInject
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_social.models.data.repo.UserRepository
import com.example.e_social.models.data.request.LoginRequest
import com.example.e_social.models.data.request.SignUpRequest
import com.example.e_social.models.domain.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val email: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> = mutableStateOf("")
    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user
    val errorMessage: MutableState<String> = mutableStateOf("")
    private val isLogin:MutableState<Boolean> = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val confirm_password: MutableState<String> = mutableStateOf("")
    init {
        getUserInfo()
    }

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
        Log.d("Login View Model", newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun getUserInfo() {
        viewModelScope.launch {

        }
    }

    fun login() {
        viewModelScope.launch {
            isLoading.value=true
            try {
                val userModelRequest =  LoginRequest(
                    email = email.value,
                    password = password.value,
                    username = email.value
                )
                isLogin.value= userRepository.login(userModelRequest).data!!.success
        } catch (e: Exception) {
            Log.d("Login",e.toString())
        }finally {
                isLoading.value=false

            }
    }
}
fun signUp(){

    viewModelScope.launch {
        isLoading.value=true
        try {
            val signUpRequest =  SignUpRequest( email = email.value, password = password.value,confirm_password=confirm_password.value, username = email.value)
            isLogin.value= userRepository.signUp(signUpRequest).data?.errors?.isEmpty() == true
        } catch (e: Exception) {
            Log.d("Login",e.toString())
        }finally {
            isLoading.value=false

        }
    }
}
fun isLogin(): Boolean {
    return isLogin.value
}
}
