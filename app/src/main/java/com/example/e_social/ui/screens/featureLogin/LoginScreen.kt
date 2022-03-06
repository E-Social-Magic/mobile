package com.example.e_social.ui.screens.featureLogin

//import com.example.e_social.ui.theme.LogInButtonColor
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_social.ui.components.CircularProgressBar
import com.example.e_social.ui.components.DefaultSnackbar
import com.example.e_social.ui.components.SnackBarController
import com.example.e_social.ui.components.TextLogoApp
import com.example.e_social.ui.screens.destinations.ForgotPasswordScreenDestination
import com.example.e_social.ui.screens.destinations.PostScreenDestination
import com.example.e_social.ui.screens.destinations.SignUpScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Destination
@Composable
fun LoginScreen(navigator: DestinationsNavigator, loginViewModel: LoginViewModel = hiltViewModel(), scaffoldState: ScaffoldState, coroutineScope: CoroutineScope, snackBarController:SnackBarController) {
    val email = loginViewModel.email.value
    val password = loginViewModel.password.value
    val focusManager = LocalFocusManager.current
    val isLoading = loginViewModel.isLoading.value
    LaunchedEffect(key1 = loginViewModel.isLogin() ){
        if(loginViewModel.isLogin())
            navigator.navigate(PostScreenDestination())

    }
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .clickable { focusManager.clearFocus() }
        ) {
            TextLogoApp()
            LoginFields(
                email,
                password,
                onEmailChange = { loginViewModel.onEmailChange(it) },
                onPasswordChange = { loginViewModel.onPasswordChange(it) }
            )
            Spacer(modifier = Modifier.height(60.dp))

            ButtonLogin(
                showMessage = {
                    snackBarController.getScope().launch {
                        snackBarController.showSnackbar(
                            snackbarHostState = scaffoldState.snackbarHostState,
                            message = "error",
                            actionLabel = "dismiss"
                        )
                    }
                },
                isLoading = isLoading
            ) {
                loginViewModel.login()

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
            }
            Spacer(modifier = Modifier.height(10.dp))

            CreateAccountButton {
                navigator.navigate(SignUpScreenDestination())
            }

            ForgotPassword {
                navigator.navigate(ForgotPasswordScreenDestination())
            }
            DefaultSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                }
            )

        }
//    }

}



@Composable
fun LoginFields(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,

    ) {
    var passwordVisualTransformation by remember {
        mutableStateOf(true)
    }
    val focusManager = LocalFocusManager.current
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
            visualTransformation = if (passwordVisualTransformation) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            shape = RoundedCornerShape(24),
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisualTransformation = !passwordVisualTransformation
                }) {
                    Icon(
                        imageVector = if (passwordVisualTransformation) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "visibility",
                        tint = Color.Black
                    )
                }
            }

        )


    }
}

@Composable
fun ButtonLogin(modifier: Modifier = Modifier, showMessage: () -> Unit,isLoading:Boolean, onLoginPress: () -> Unit) {
    Button(
        onClick = {
//            if()
            onLoginPress.invoke()
        },
        modifier = modifier
            .width(197.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        shape = RoundedCornerShape(25),
        enabled = !isLoading
    ) {
        if(isLoading)
            CircularProgressBar(isDisplay = isLoading)
        else
            Text(
                "Log In",
                fontSize = 17.sp,
            )
    }
}

@Composable
fun CreateAccountButton(modifier: Modifier = Modifier, onCreateAccountClick: () -> Unit) {
    OutlinedButton(
        onClick = { onCreateAccountClick.invoke() },
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
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 14.sp)) {
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

