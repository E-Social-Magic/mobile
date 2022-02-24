package com.example.e_social.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_social.ui.components.SnackBarController
import com.example.e_social.ui.components.TextLogoApp
import com.example.e_social.viewmodels.LoginViewModel

@Composable
fun SignUpScreen(loginViewModel: LoginViewModel = viewModel()) {
    val email by loginViewModel.email.observeAsState("")
    val password by loginViewModel.password.observeAsState("")
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val snackBarController = SnackBarController(coroutineScope)
    val scaffoldState = rememberScaffoldState()
    Box(modifier = Modifier.fillMaxSize()) {
       Column(horizontalAlignment = Alignment.CenterHorizontally) {
           TextLogoApp()
           SignUpFields(
               email,
               password,
               onEmailChange = { loginViewModel.onEmailChange(it) },
               onPasswordChange = { loginViewModel.onPasswordChange(it) })
       }
        RedirectLoginPage{

        }
       }
}

@Composable
fun SignUpFields(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp),
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
            placeholder = { Text(text = "Eg:098654321") },
            label = { Text(text = "phone") },
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
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
            shape = RoundedCornerShape(24)

        )
        OutlinedTextField(
            value = password,
            placeholder = { Text(text = "confirm password") },
            label = { Text(text = "confirm password") },
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            shape = RoundedCornerShape(24)

        )
    }
}
@Composable
fun ButtonSignUp(modifier: Modifier = Modifier, onSignUpPress: () -> Unit) {
    Button(
        onClick = {
            onSignUpPress.invoke()
        },
        modifier = modifier
            .width(210.dp)
            .height(64.dp)
            .padding(bottom = 12.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        shape = RoundedCornerShape(24),
    ) {
        Text(
            "Sign Up",
            fontSize = 17.sp,
        )
    }
}

@Composable
fun RedirectLoginPage(onForgotPasswordClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxHeight()) {
        ClickableText(
            text = AnnotatedString("Do you have an account?"),
            style = MaterialTheme.typography.caption,
            modifier =
            Modifier
                .padding(bottom = 36.dp)
                .align(Alignment.BottomCenter),
            onClick = { onForgotPasswordClick }
        )

    }
}
