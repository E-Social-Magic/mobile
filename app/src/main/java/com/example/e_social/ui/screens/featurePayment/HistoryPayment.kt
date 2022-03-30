package com.example.e_social.ui.screens.featurePayment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_social.ui.components.CircularProgressBar
import com.example.e_social.ui.components.SimpleTopAppBar
import com.example.e_social.ui.screens.featureProfile.ProfileTransactionHistory
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun HistoryPayment(
    navigator: DestinationsNavigator, paymentViewModel: PaymentViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        paymentViewModel.getHistoryPayment()
    }
    val paymentsHisotry = paymentViewModel.payment.value
    val avatar = paymentViewModel.avatar.value
    val isLoading = paymentViewModel.isLoading.value
    Box(Modifier.fillMaxSize()) {
        Column {
            SimpleTopAppBar(
                title = "Lịch sử số dư",
            ) {
                navigator.navigateUp()
            }
            when (isLoading) {
                true -> {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressBar(isDisplay = isLoading)
                    }
                }
                else -> {
                    Column(modifier = Modifier.fillMaxSize(),) {
                        ProfileTransactionHistory(paymentList = paymentsHisotry, avatar = avatar)
                    }
                }
            }
        }

    }
}
