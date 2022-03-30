package com.example.e_social.ui.components

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackBarController(private val scope: CoroutineScope){

    private var snackBarJob: Job?=null

    init {
        cancelActiveSnack()
    }
    fun getScope() = scope

    fun showSnackbar(  snackbarHostState: SnackbarHostState, message: String, actionLabel: String){

        if (snackBarJob == null){
            snackBarJob = scope.launch {
                snackbarHostState.showSnackbar(message = message,actionLabel = actionLabel)
                cancelActiveSnack()
            }
        }
        else{
            cancelActiveSnack()
            snackBarJob = scope.launch {
               snackbarHostState.showSnackbar(message = message,actionLabel =actionLabel)
                cancelActiveSnack()
            }

        }
    }
    private fun cancelActiveSnack(){
        snackBarJob?.let{ job ->
            job.cancel()
            snackBarJob = Job()
        }
    }
}