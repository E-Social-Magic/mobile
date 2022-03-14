package com.example.e_social.util

import com.google.gson.Gson
import okhttp3.ResponseBody


object ErrorUtils {
    inline fun <reified T> parseError(errorBody: ResponseBody): T {
        return Gson().fromJson(errorBody.charStream(), T::class.java)
    }
}

