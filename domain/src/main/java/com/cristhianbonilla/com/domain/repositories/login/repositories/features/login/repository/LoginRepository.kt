package com.cristhianbonilla.com.domain.repositories.login.repositories.features.login.repository

import android.content.Context
import androidx.preference.PreferenceManager
import com.cristhianbonilla.com.domain.dtos.UserDto

class LoginRepository :
    LoginInterfaceRepository {

    override fun saveUser(user: UserDto, contex:Context) {


    }

    override fun saveUserPreference(user: UserDto, context: Context) {
        val settings =
            context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val editor = settings.edit()

        editor.putString("userName", user.name)
        editor.putString("userEmail", user.email)
        editor.putString("userPhone", user.phone)
        editor.apply()


    }

    override fun getUserPreference(key: String, context: Context) {
        val settings =
            context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val editor = settings.edit()

        settings.getString("userName",null)
        settings.getString("userEmail",null)
        settings.getString("userPhone",null)
    }

    override fun getUserNamePreference(key: String, context: Context): String? {
        val settings = context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        return settings.getString("userName",null)
    }

    override fun deleteUserPreference(key: String, contex: Context) {

    }
}