package com.example.e_social

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_social.ui.theme.EsocialTheme
import com.example.e_social.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EsocialTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LoginView()
                }
            }
        }
    }
}

@Composable
fun LoginView(loginViewModel: LoginViewModel = viewModel() )
{
    val email by loginViewModel.email.observeAsState("")
    val password by loginViewModel.password.observeAsState("")
    val focusManager = LocalFocusManager.current
//    val user by viewModel.user.observeAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
            .clickable { focusManager.clearFocus() }
    ) {
        LoginFields(
            email,
            password,
            onLoginClick = { "ttiganik@gmail.com" },
            onEmailChange = { loginViewModel.onEmailChange(it) },
            onPasswordChange = { loginViewModel.onPasswordChange(it) }
        )
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch {
            val result = snackbarHostState
                .showSnackbar(
                    "Login error: ",
                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Indefinite
                )
            when (result) {
//                SnackbarResult.ActionPerformed -> viewModel.goToInitialState()
            }
        }

        SnackbarHost(hostState = snackbarHostState)
    }
//        when (user?.status) {
//            Status.LOADING -> {
//                CircularProgressIndicator()
//            }
//            Status.SUCCESS -> {
//                if (user?.data == null) {
//                    LoginFields(
//                        email,
//                        password,
//                        onLoginClick = { viewModel.login("ttiganik@gmail.com", password) },
//                        onEmailChange = { email = it },
//                        onPasswordChange = { password = it }
//                    )
//                } else {
//                    Text("Login successful", fontSize = MaterialTheme.typography.h3.fontSize)
//                    Text(
//                        "User ${user?.data?.login}",
//                        fontSize = MaterialTheme.typography.h4.fontSize
//                    )
//                }
//            }
//            Status.ERROR -> {
//                val snackbarHostState = remember { SnackbarHostState() }
//                val coroutineScope = rememberCoroutineScope()
//
//
//        }
    //}
}

@Composable
fun LoginFields(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Please login")

        OutlinedTextField(
            value = email,
            placeholder = { Text(text = "user@email.com") },
            label = { Text(text = "email") },
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )

        OutlinedTextField(
            value = password,
            placeholder = { Text(text = "password") },
            label = { Text(text = "password") },
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )

        Button(onClick = {
            if (email.isBlank() == false && password.isBlank() == false) {
                onLoginClick(email)
                focusManager.clearFocus()
            } else {
//                Toast.makeText(
//                    context,
//                    "Please enter an email and password",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        }) {
            Text("Login")
        }
    }
}
