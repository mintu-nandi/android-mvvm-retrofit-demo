package com.example.demo.util

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtil {
    const val preferencesName = "MoneyBoxPreferences"
    private const val USER_TOKEN = "TOKEN"
    private const val USER_NAME = "USERNAME"

    fun sharedPreference(context: Context): SharedPreferences = context.getSharedPreferences(
        preferencesName, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.token
        get() = getString(USER_TOKEN, "")
        set(value) {
            editMe {
                it.putString(USER_TOKEN, value)
            }
        }

    var SharedPreferences.username
        get() = getString(USER_NAME, "")
        set(value) {
            editMe {
                it.putString(USER_NAME, value)
            }
        }

    var SharedPreferences.clearValues
        get() = { }
        set(value) {
            editMe {
                it.clear()
            }
        }

}