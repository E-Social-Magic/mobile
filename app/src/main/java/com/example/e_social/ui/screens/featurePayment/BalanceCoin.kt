package com.example.e_social.ui.screens.featurePayment

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.twotone.History
import androidx.compose.material.icons.twotone.Login
import androidx.compose.material.icons.twotone.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_social.R
import com.example.e_social.ui.components.SimpleTopAppBar
import com.example.e_social.ui.screens.destinations.BalanceCoinDestination
import com.example.e_social.ui.screens.destinations.DepositCoinsDestination
import com.example.e_social.ui.screens.destinations.HistoryPaymentDestination
import com.example.e_social.ui.screens.destinations.WithDrawDestination
import com.example.e_social.ui.screens.featureLogin.LoginViewModel
import com.example.e_social.ui.screens.featureProfile.HorizontalDivider
import com.example.e_social.ui.screens.featureProfile.VerticalDivider
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.DecimalFormat
import java.text.NumberFormat

@Destination
@Composable
fun BalanceCoin(
    navigator: DestinationsNavigator,
    paymentViewModel: PaymentViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    ) {
    loginViewModel.isShowBottomBar.value=false
    val formatter: NumberFormat = DecimalFormat("#,###")
    val coins = paymentViewModel.coins
    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.TopStart)) {
            SimpleTopAppBar(title = "S??? d?? ") {
                navigator.navigateUp()
            }
            BalanceHeader(navigator = navigator)
            HorizontalDivider()
        }
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.money), contentDescription = "Money")
            Text(
                text = formatter.format(coins) + " coins",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }
}

@Composable
fun BalanceHeader(navigator: DestinationsNavigator) {
    Row(
        Modifier.height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        VerticalDivider()
        Row(Modifier
            .weight(1f)
            .fillMaxWidth()
            .clickable {navigator.navigate(DepositCoinsDestination) }
            .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.TwoTone.Login, contentDescription = "Nap tien")
            Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "N???p ti???n", fontSize = 16.sp,fontWeight = FontWeight.Bold)
            }
        }

        VerticalDivider()
        Row(Modifier
            .fillMaxHeight()
            .weight(1f)
            .clickable { navigator.navigate(WithDrawDestination) }
            .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Filled.Logout, contentDescription = "Rut tien")
            Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "R??t ti???n", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
        VerticalDivider()
        Row(Modifier
            .fillMaxHeight()
            .weight(1f)
            .clickable {navigator.navigate(HistoryPaymentDestination) }
            .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.TwoTone.History, contentDescription = "Nap tien")
            Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "L???ch s??? ", fontSize = 16.sp,fontWeight = FontWeight.Bold)
            }
        }
    }
}