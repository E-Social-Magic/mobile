package com.example.e_social.ui.screens.featureLogin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.e_social.ui.screens.destinations.LoginScreenDestination
import com.example.e_social.ui.screens.destinations.TopicListScreenDestination
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Destination
@Composable
fun SignUpScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    snackBarController: SnackBarController
) {
    val email = loginViewModel.email.value
    val password = loginViewModel.password.value
    val confirmPassword = loginViewModel.confirmPassword.value
    val phone = loginViewModel.phone.value
    val focusManager = LocalFocusManager.current
    val isLoading = loginViewModel.isLoading.value

    Box(contentAlignment = Alignment.BottomCenter) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Row(Modifier.weight(2f)) {
                TextLogoApp()
            }
            Column(
                Modifier
                    .weight(6f)
                    .verticalScroll(rememberScrollState())
            ) {
                SignUpFields(
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword,
                    phone = phone,
                    onEmailChange = { loginViewModel.onEmailChange(it) },
                    onPhoneChange = { loginViewModel.onPhoneChange(it) },
                    onPasswordChange = { loginViewModel.onPasswordChange(it) },
                    onConfirmPasswordChange = { loginViewModel.onConfirmPasswordChange(it) }
                )
            }

            Row(Modifier.weight(2f)) {
                ButtonSignUp(isLoading = isLoading) {
                    coroutineScope.launch(Dispatchers.Main) {
                        loginViewModel.signUp()
                        if (loginViewModel.errorMessage.value.isEmpty()) {
                            navigator.navigate(TopicListScreenDestination)
                        } else {
                            snackBarController.getScope().launch {
                                snackBarController.showSnackbar(
                                    snackbarHostState = scaffoldState.snackbarHostState,
                                    message = loginViewModel.errorMessage.value[0],
                                    actionLabel = "dismiss"
                                )
                                loginViewModel.errorMessage.value = listOf()
                            }
                        }
                    }
                }
            }
            Row(Modifier.weight(1f)) {
                RedirectLoginPage {
                    navigator.navigate(LoginScreenDestination())
                }
            }
        }
        DefaultSnackbar(snackbarHostState = scaffoldState.snackbarHostState, onDismiss = {
            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        })

    }
}

@Composable
fun SignUpFields(
    email: String,
    password: String,
    confirmPassword: String,
    phone: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit
) {
    var passwordVisualTransformation by remember {
        mutableStateOf(true)
    }
    var passwordConfirmVisualTransformation by remember {
        mutableStateOf(true)
    }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            OutlinedTextField(
                value = email,
                placeholder = { Text(text = "user@email.com") },
                label = { Text(text = "Email ") },
                onValueChange = onEmailChange,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                shape = RoundedCornerShape(24),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            OutlinedTextField(
                value = phone,
                placeholder = { Text(text = "Eg:098654321") },
                label = { Text(text = "Phone") },
                onValueChange = onPhoneChange,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Phone
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                shape = RoundedCornerShape(24),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            OutlinedTextField(
                value = password,
                placeholder = { Text(text = "Password") },
                label = { Text(text = "Password") },
                onValueChange = onPasswordChange,
                visualTransformation = if (passwordVisualTransformation) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                shape = RoundedCornerShape(24),
                modifier = Modifier.padding(vertical = 8.dp)


            )
            OutlinedTextField(
                value = confirmPassword,
                placeholder = { Text(text = "Confirm password") },
                label = { Text(text = "Confirm password") },
                onValueChange = onConfirmPasswordChange,
                visualTransformation = if (passwordConfirmVisualTransformation) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                shape = RoundedCornerShape(24),
                modifier = Modifier.padding(vertical = 8.dp)
            )

    }
}

@Composable
fun ButtonSignUp(modifier: Modifier = Modifier, isLoading: Boolean, onSignUpPress: () -> Unit) {
    Column(verticalArrangement = Arrangement.Center) {
        Button(
            onClick = {
                onSignUpPress.invoke()
            },
            modifier = modifier
                .width(197.dp)
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(25),
            enabled = !isLoading
        ) {
            if (isLoading)
                CircularProgressBar(isDisplay = isLoading)
            else
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
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 14.sp)) {
                    append("Do you have an account? ")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold
                        ),
                    ) {
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
