package com.example.e_social.ui.screens.featureLogin

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_social.models.data.repo.user.UserRepository
import com.example.e_social.models.data.request.LoginRequest
import com.example.e_social.models.data.request.SignUpRequest
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository, val sessionManager: SessionManager) : ViewModel() {
    val email: MutableState<String> = mutableStateOf("long2000@gmail.com")
    val password: MutableState<String> = mutableStateOf("123456")
    val confirmPassword = mutableStateOf("")
    val phone = mutableStateOf("")
    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user
    var errorMessage: MutableState<List<String>> = mutableStateOf(listOf())
    val isLogin = mutableStateOf(false)
    val isShowTopBar = mutableStateOf(isLogin.value)
    val isShowBottomBar = mutableStateOf(isLogin.value)
    val isLoading = mutableStateOf(false)
    val sliderValue: MutableState<Float> = mutableStateOf(0f)
    val steps = mutableStateOf(1.5f)
    val levels = listOf(6,7,8,9,10,11,12)
    var level = mutableStateOf(levels[0])
    var topicSelected= mutableListOf<String>()

    init {
        getUserInfo()
    }

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun onPhoneChange(newPhone: String) {
        phone.value = newPhone
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
        }
    }

    fun onChangSlider(sliderValueChange: Float) {
        sliderValue.value = sliderValueChange
    }

    suspend fun login() {
        isLoading.value=true
        val loginRequest = LoginRequest(
            email = email.value,
            password = password.value,
        )
        val response = userRepository.login(loginRequest)
        val checkError= response.data?.message
        if (checkError.isNullOrBlank()) {
            val userResponse = response.data!!
            _user.value = UserModel(
                username = userResponse.username,
                token = userResponse.token,
                email = userResponse.email,
                id = userResponse.id,
                role = userResponse.role,
                avatar = userResponse.avatar?:"https://gaplo.tech/content/images/2020/03/android-jetpack.jpg"
            )
            sessionManager.saveAuthToken(userResponse.token)
            sessionManager.saveUserId(userResponse.id)
            isLogin.value=true
        }
        else{
            errorMessage.value= listOf(checkError)
        }
        isLoading.value = false

    }

    suspend fun signUp() {
        isLoading.value = true
        val signUpRequest = SignUpRequest(
            email = email.value,
            password = password.value,
            confirm = confirmPassword.value,
            username = email.value
        )
        val response = userRepository.signUp(signUpRequest)
        errorMessage.value = response.data?.errors?.map { it.msg } ?: listOf()
        if (errorMessage.value.isEmpty()) {
            val userResponse = response.data!!
            if (userResponse != null) {
                login()
            }
        }
        isLoading.value = false
    }

}
