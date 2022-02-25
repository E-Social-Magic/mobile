package com.example.e_social.ui.screens

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontFamily.Companion.Monospace
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_social.ui.components.DefaultSnackbar
import com.example.e_social.ui.components.SnackBarController
import com.example.e_social.ui.components.TextLogoApp
//import com.example.e_social.ui.theme.LogInButtonColor
import com.example.e_social.ui.theme.Purple200
import com.example.e_social.viewmodels.LoginViewModel
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    val email by loginViewModel.email.observeAsState("")
    val password by loginViewModel.password.observeAsState("")
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val snackBarController = SnackBarController(coroutineScope)
    val scaffoldState = rememberScaffoldState()
//    var data = loginViewMode


    Scaffold(scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        }) {
        Column(
            verticalArrangement = Arrangement.SpaceAround    ,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .clickable { focusManager.clearFocus() }
        ) {
//            when (data) {
//                is Result.Loading ->{ CircularProgressIndicator() }
//
//                is Result.Success -> {
//                    if ( "check reponse data"?.equals("")) {
//                        Text(text = "Login fail")
//                    } else {
//                        Text("Login successful", fontSize = MaterialTheme.typography.h3.fontSize)
//                        Text(
//                            "User name",
//                            fontSize = MaterialTheme.typography.h4.fontSize
//                        )
//                    }
//                }
//                is Result.Error -> {
//                    val snackbarHostState = remember { SnackbarHostState() }
//                    val coroutineScope = rememberCoroutineScope()
//
//
//                }
            TextLogoApp()
            LoginFields(
                email,
                password,
                onEmailChange = { loginViewModel.onEmailChange(it) },
                onPasswordChange = { loginViewModel.onPasswordChange(it) }
            )
            Spacer(modifier =Modifier.height(60.dp))

            ButtonLogin {
                snackBarController.getScope().launch {
                    snackBarController.showSnackbar(
                        snackbarHostState = scaffoldState.snackbarHostState,
                        message = "error",
                        actionLabel = "dismiss"
                    )
                }
            }
            Spacer(modifier =Modifier.height(10.dp))

            CreateAccountButton() {

            }

            ForgotPassword() {

            }
            DefaultSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                }
            )

        }
    }
}


@Composable
fun LoginFields(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,

    ) {
    val focusManager = LocalFocusManager.current
//    val context = LocalContext.current
//            if (email.isBlank() == false && password.isBlank() == false) {
//                focusManager.clearFocus()
//            } else {
//                Toast.makeText(
//                    context,
//                    "Please enter an email and password",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            placeholder = { Text(text = "user@email.com") },
            label = { Text(text = "phone or email ") },
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            shape = RoundedCornerShape(24)
        )

        OutlinedTextField(
            value = password,
            placeholder = { Text(text = "password") },
            label = { Text(text = "password") },
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            shape = RoundedCornerShape(24),
            trailingIcon = {
                IconButton(onClick = {
//                    cPasswordVisibility.value = !cPasswordVisibility.value
                }) {
                    Icon(
                        imageVector = if (true) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "visibility",
                        tint = Color.Black
                    )
                }
            }

        )


    }
}

@Composable
fun ButtonLogin(modifier: Modifier = Modifier, onLoginPress: () -> Unit) {
    Button(
        onClick = {
            onLoginPress.invoke()
                  },
        modifier = modifier
            .width(197.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        shape = RoundedCornerShape(25),
    ) {
        Text(
            "Log In",
            fontSize = 17.sp,
        )
    }
}

@Composable
fun CreateAccountButton(modifier: Modifier = Modifier, onCreateAccountClick: () -> Unit) {
    OutlinedButton(
        onClick = { onCreateAccountClick },
        modifier = modifier
            .width(197.dp)
            .height(48.dp),
        shape = RoundedCornerShape(25),
    ) {
        Text(
            "Create Account?",
            fontSize = 17.sp
        )
    }
}

@Composable
fun ForgotPassword(onForgotPasswordClick: () -> Unit) {
   Box(modifier = Modifier.fillMaxHeight()) {
       ClickableText(
           text = buildAnnotatedString{
               withStyle(style = SpanStyle(fontSize = 14.sp)){
                   append("Forgot password? ")
               }

           },
           style = MaterialTheme.typography.caption,
           modifier =
           Modifier
               .padding(bottom = 36.dp)
               .align(Alignment.BottomCenter),
           onClick = { onForgotPasswordClick }
       )

   }
}


