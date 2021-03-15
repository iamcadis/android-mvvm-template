package com.apps.data.local

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPrefs @Inject constructor(@ApplicationContext context : Context) {

    private val prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    var authToken: String?
        get() = prefs.getString(AUTH_TOKEN, null)
        set(value) {
            prefs.edit { putString(AUTH_TOKEN, value) }
        }

    var username: String
        get() = prefs.getString(USERNAME, "") ?: ""
        set(value) {
            prefs.edit { putString(USERNAME, value) }
        }

    var password: String
        get() = prefs.getString(PASSWORD, "") ?: ""
        set(value) {
            prefs.edit { putString(PASSWORD, value) }
        }

    var role: String
        get() = prefs.getString(ROLE, "") ?: ""
        set(value) {
            prefs.edit { putString(ROLE, value) }
        }

    var currentUser: String?
        get() = prefs.getString(CURRENT_USER, null)
        set(value) {
            prefs.edit { putString(CURRENT_USER, value) }
        }

    fun clear() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val SHARED_PREF_NAME = "DB::USER_PREFERENCES"
        private const val AUTH_TOKEN = "AUTH_TOKEN"
        private const val CURRENT_USER = "CURRENT_USER"
        private const val USERNAME = "USERNAME"
        private const val PASSWORD = "PASSWORD"
        private const val ROLE = "ROLE"
    }
}