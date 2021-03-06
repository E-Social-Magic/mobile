package com.example.e_social.ui.screens.featureLogin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_social.models.data.repo.user.UserRepository
import com.example.e_social.models.data.request.LoginRequest
import com.example.e_social.models.data.request.SignUpRequest
import com.example.e_social.models.data.response.TopicList
import com.example.e_social.models.domain.model.UserModel
import com.example.e_social.util.Resource
import com.example.e_social.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    val sessionManager: SessionManager
) : ViewModel() {
    val email: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> = mutableStateOf("")
    val userName = mutableStateOf("")
    val confirmPassword = mutableStateOf("")
    val phone = mutableStateOf("0")
    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user
    var errorMessage: MutableState<List<String>> = mutableStateOf(listOf())
    val isLogin = mutableStateOf(false)
    val isShowTopBar = mutableStateOf(false)
    val isShowBottomBar = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val sliderValue: MutableState<Float> = mutableStateOf(0f)
    val steps = mutableStateOf(1.5f)
    val levels = listOf("6", "7", "8", "9", "10", "11", "12","khác")

    var level = mutableStateOf(levels[0])
    var topicSelected = mutableListOf<String>()

    init {
    }

    fun onEmailChange(newEmail: String) {
        email.value = newEmail.trim()
    }

    fun onPasswordChange(newPassword: String) {

        password.value = newPassword.trim()
    }

    fun onPhoneChange(newPhone: String) {
        phone.value = newPhone.trim()
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }
    fun onUserNameChange(newUserName: String) {
        userName.value = newUserName
    }

    fun logOut(){
        isLogin.value= false
        sessionManager.saveAuthToken("")
        sessionManager.saveAuthCoins("0")
        sessionManager.saveUserId("")

    }
    private fun getCoins() {
        viewModelScope.launch{
            isLoading.value = true

            val result = userRepository.getUserInfo()
            try {
                when (result) {
                    is Resource.Success -> {
                        if (result.data!=null){
                            sessionManager.saveAuthCoins(result.data.user.coins.toString())
                        }
                    }

                    is Resource.Error -> {
                    }
                    else -> {
                    }
                }
            } catch (e: Exception) {
            }
            isLoading.value = false
        }
    }

    fun onChangSlider(sliderValueChange: Float) {
        sliderValue.value = sliderValueChange
    }

    suspend fun login() {
        isLoading.value = true
        val loginRequest = LoginRequest(
            email = email.value,
            password = password.value,
        )
        val response = userRepository.login(loginRequest)
        val checkError = response.data?.message
        if (checkError.isNullOrBlank()) {
            val userResponse = response.data!!
            _user.value = UserModel(
                username = userResponse.username,
                token = userResponse.token,
                email = userResponse.email,
                id = userResponse.id,
                role = userResponse.role,
                avatar = userResponse.avatar
            )
            sessionManager.saveAuthToken(userResponse.token)
            sessionManager.saveUserId(userResponse.id)
            getCoins()
            isLogin.value = true
        } else {
            errorMessage.value = listOf(checkError)
        }
        isLoading.value = false

    }

    suspend fun signUp() {
        isLoading.value = true
        val signUpRequest = SignUpRequest(
            email = email.value,
            password = password.value,
            phone =phone.value,
            confirm = confirmPassword.value,
            username = userName.value
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
    fun finishSignUp(topicList: List<String>){
        viewModelScope.launch {
            isLoading.value = true
            val response = userRepository.joinGroups(topicList)
            isLoading.value =false
        }
    }
}
