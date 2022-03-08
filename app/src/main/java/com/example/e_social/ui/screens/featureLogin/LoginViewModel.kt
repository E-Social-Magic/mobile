package com.example.e_social.ui.screens.featureLogin

//import androidx.hilt.lifecycle.ViewModelInject
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_social.models.data.repo.user.UserRepository
import com.example.e_social.models.data.repo.user.impl.UserRepositoryImpl
import com.example.e_social.models.data.request.LoginRequest
import com.example.e_social.models.data.request.SignUpRequest
import com.example.e_social.models.domain.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.android.scopes.ViewScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val email: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> = mutableStateOf("")
    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user
    val errorMessage: MutableState<String> = mutableStateOf("")
    val isLogin = mutableStateOf(true)
    val isLoading = mutableStateOf(false)
    val confirm_password: MutableState<String> = mutableStateOf("")
    val sliderValue: MutableState<Float> = mutableStateOf(0f)
    val steps = mutableStateOf(1f)
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
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
    fun onChangSlider(sliderValueChange:Float){
        sliderValue.value =sliderValueChange
    }
    fun login(loginRequest:LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value=true
            try {
                isLogin.value= userRepository.login(loginRequest).data?.success ?: false
        } catch (e: Exception) {
            Log.d("Login",e.toString())
        }finally {
                isLoading.value=false
            }
    }
}
fun signUp(){

    viewModelScope.launch(Dispatchers.IO) {
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
}
