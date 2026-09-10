package com.habitAt.app.data

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("habitAt_user_prefs", Context.MODE_PRIVATE)

    var nickname: String
        get() = prefs.getString(KEY_NICKNAME, "") ?: ""
        set(value) {
            prefs.edit().putString(KEY_NICKNAME, value.trim()).apply()
        }

    var isOnboardingCompleted: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
        set(value) {
            prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, value).apply()
        }

    companion object {
        private const val KEY_NICKNAME = "user_nickname"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }
}
