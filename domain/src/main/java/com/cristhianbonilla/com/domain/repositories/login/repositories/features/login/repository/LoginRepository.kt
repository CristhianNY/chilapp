package com.cristhianbonilla.com.domain.repositories.login.repositories.features.login.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.cristhianbonilla.com.domain.dtos.UserDto
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class LoginRepository :
    LoginInterfaceRepository {

    private var mDatabase: DatabaseReference? = null

    override fun saveUser(user: UserDto, contex:Context) {

        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase!!.child("User").child(user.userId).setValue(user)

        saveUserPreference(user,contex)
    }

    override fun saveUserPreference(user: UserDto, context: Context) {
        val settings =
            context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val editor = settings.edit()

        editor.putString("userName", user.name)
        editor.putString("userEmail", user.email)
        editor.putString("userPhone", user.phone)
        editor.putString("userID", user.userId)
        editor.putString("usetBirthDate", user.birthDate)
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

    override fun deleteUserPreference(context: Context) {
        val settings =
            context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        settings.edit().clear().commit()
    }
}