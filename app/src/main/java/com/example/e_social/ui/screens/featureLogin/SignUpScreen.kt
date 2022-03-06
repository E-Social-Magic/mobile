package com.example.e_social.ui.screens.featureLogin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_social.ui.components.SnackBarController
import com.example.e_social.ui.components.TextLogoApp
import com.example.e_social.ui.screens.destinations.*

import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope

@Destination
@Composable
fun SignUpScreen(loginViewModel: LoginViewModel = hiltViewModel(), navigator: DestinationsNavigator, scaffoldState: ScaffoldState, coroutineScope : CoroutineScope, snackBarController:SnackBarController) {
    val email = loginViewModel.email.value
    val password = loginViewModel.password.value
    val focusManager = LocalFocusManager.current

    if (loginViewModel.isLogin())
        navigator.navigate(PostScreenDestination())
    Box(modifier = Modifier.fillMaxSize()) {
       Column(horizontalAlignment = Alignment.CenterHorizontally) {
           TextLogoApp()
           SignUpFields(
               email,
               password,
               onEmailChange = { loginViewModel.onEmailChange(it) },
               onPasswordChange = { loginViewModel.onPasswordChange(it) })
           ButtonSignUp(){
               loginViewModel.signUp()
           }
           RedirectLoginPage{
               navigator.navigate(LoginScreenDestination())
           }
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
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
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
    Column(verticalArrangement = Arrangement.Center,modifier = Modifier.height(210.dp)) {
        Button(
            onClick = {
                onSignUpPress.invoke()
            },
            modifier = modifier
                .width(197.dp)
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(25),
        ) {
            Text(
                "Sign Up",
                fontSize = 17.sp,
            )
        }

    }
}

@Composable
fun RedirectLoginPage(onForgotPasswordClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxHeight()) {
        ClickableText(
            text = buildAnnotatedString{
                withStyle(style = SpanStyle(fontSize = 14.sp)){
                    append("Do you have an account? ")
                    withStyle(style = SpanStyle(color = Color.Blue,fontWeight = FontWeight.Bold),) {
                        append("Login")
                    }
                }

            },
            style = MaterialTheme.typography.caption,
            modifier =
            Modifier
                .padding(bottom = 36.dp)
                .align(Alignment.BottomCenter),
            onClick = { onForgotPasswordClick.invoke() }
        )

    }
}