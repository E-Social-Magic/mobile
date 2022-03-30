package com.example.e_social.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.e_social.R

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString("token", token)
        editor.apply()
    }
    fun saveAuthCoins(coins: String) {
        val editor = prefs.edit()
        editor.putString("coins", coins)
        editor.apply()
    }
    fun saveUserId(userId: String) {
        val editor = prefs.edit()
        editor.putString("userId", userId)
        editor.apply()
    }
    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString("token", null)
    }
    fun fetchCoin(): Long {
        val coins =prefs.getString("coins", null)
        if (coins==null)
            return  0
        else
            return coins.toLong()
    }
    fun fetchUserId(): String? {
        return prefs.getString("userId", null)
    }
}