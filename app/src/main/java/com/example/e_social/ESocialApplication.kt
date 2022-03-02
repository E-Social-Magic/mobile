package com.example.e_social

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ESocialApplication : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}