package com.example.musicmobileapp.security

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class UserSecurityManager(private val sharedPreferences: SharedPreferences) {

    fun saveUserSession(userId: String, sessionUUID: String) {
        with(sharedPreferences.edit()) {
            putString("user_id", userId)
            putString("session_uuid", sessionUUID)
            apply()
        }
    }

    fun getUserSession(): Pair<String?, String?> {
        val userId = sharedPreferences.getString("user_id", null)
        val sessionUUID = sharedPreferences.getString("session_uuid", null)
        return Pair(userId, sessionUUID)
    }

    fun clearUserSession() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}