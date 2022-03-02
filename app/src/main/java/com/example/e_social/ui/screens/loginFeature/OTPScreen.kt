package com.example.e_social.ui.screens.loginFeature

import android.text.Layout
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_social.R
import com.example.e_social.ui.theme.Green400
import com.example.e_social.ui.theme.Grey100
import com.example.e_social.ui.theme.Purple500
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun OTPScreen(navigator: DestinationsNavigator,modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val otpVal: String? = null
    val email = "Text email"
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(14.dp)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Enter your PIN",
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                text = "Please enter the 6-digit code that includes in :$email",
                modifier = Modifier.padding(top = 20.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                OTPTextFields(
                    length = 6
                ) { getOpt ->
                    otpVal
                }

                Spacer(modifier = Modifier.height(30.dp))

                Box {
                    ClickableText(text = AnnotatedString(text = "Resend code"), modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterStart), onClick = {})
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Button(
                        onClick = {
                            if (otpVal != null) {
                                Toast.makeText(context, "Please Enter Otp", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Green400),
                        modifier = Modifier
                            .height(45.dp)
                            .align(alignment = Alignment.CenterEnd),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Next",
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                }

            }
        }

    }

}

@Destination
@Composable
fun VerifyInput(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Grey100)
            .padding(14.dp),
    )
    {
        Column(
            modifier = Modifier
                .shadow(elevation = 4.dp)
                .weight(0.5f)
                .background(Color.White)
                .border(
                    width = 0.1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(14.dp),
            verticalArrangement = Arrangement.Center
            ) {
            Text(
                text = "Enter your Phone Number ",
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Verification code will be sent to your phone number",
                modifier = Modifier.padding(top = 20.dp)
            )
            Spacer(modifier = Modifier
                .height(25.dp)
                .shadow(elevation = 6.dp))
            Surface(
                elevation = 6.dp
            ) {
                OutlinedTextField(
                    textStyle = MaterialTheme.typography.body1.copy(
                        textAlign = TextAlign.Start, color = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    singleLine = true,
                    value = "088888877777",
                    onValueChange = { value: String ->
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(24)
                )
            }
            SendCodeButton() {
//                navigator.navigate()
            }
        }
        Column(modifier = Modifier.weight(0.5f)) {

        }
    }
}


@Composable
fun OTPTextFields(
    modifier: Modifier = Modifier,
    length: Int,
    onFilled: (code: String) -> Unit
) {
    var code: List<Char> by remember { mutableStateOf(listOf()) }
    val focusRequesters: List<FocusRequester> = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(length) {
            temp.add(FocusRequester())
        }
        temp
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        (0 until length).forEach { index ->
            Surface(elevation = 6.dp, modifier = Modifier.shadow(elevation = 6.dp)) {
                OutlinedTextField(
                    modifier = Modifier
                        .height(45.dp)
                        .width(45.dp)
                        .background(color = Color(0xFFE9E8E8))
                        .focusOrder(focusRequester = focusRequesters[index]) {
                            focusRequesters[index + 1].requestFocus()
                        },
                    textStyle = MaterialTheme.typography.body2.copy(
                        textAlign = TextAlign.Center, color = Color.Black
                    ),
                    singleLine = true,
                    value = code.getOrNull(index = index)?.takeIf {
                        it.isDigit()
                    }?.toString() ?: "",
                    onValueChange = { value: String ->
                        if (focusRequesters[index].freeFocus()) {
                            val temp = code.toMutableList()
                            if (value == "") {
                                if (temp.size > index) {
                                    temp.removeAt(index = index)
                                    code = temp
                                    focusRequesters.getOrNull(index - 1)?.requestFocus()
                                }
                            } else {
                                if (code.size > index) {
                                    temp[index] = value.getOrNull(0) ?: ' '
                                } else {
                                    temp.add(value.getOrNull(0) ?: ' ')
                                    code = temp
                                    focusRequesters.getOrNull(index + 1)?.requestFocus()
                                        ?: onFilled(
                                            code.joinToString(separator = "")
                                        )
                                }
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        }
    }
}

@Composable
fun SendCodeButton(modifier: Modifier = Modifier, onPress: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                onPress.invoke()
            },
            modifier = modifier
                .width(197.dp)
                .height(48.dp)
                .align(alignment = Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(backgroundColor = Green400),
            shape = RoundedCornerShape(25),
        ) {
            Text(
                "Send code",
                fontSize = 17.sp,
            )
        }
    }
}


