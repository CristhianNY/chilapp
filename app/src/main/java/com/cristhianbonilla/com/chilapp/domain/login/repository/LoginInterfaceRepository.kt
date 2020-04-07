package com.cristhianbonilla.com.chilapp.domain.login.repository

import android.content.Context
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.google.firebase.database.DatabaseReference

interface LoginInterfaceRepository {

    fun saveUser(user: UserDto, contex:Context)
    fun saveUserPreference(user: UserDto, contex:Context)
    fun getUserPreference(key:String, contex:Context)
    fun getUserNamePreference(key:String, contex:Context) : String?
    suspend fun getUserFromFirebase(user: UserDto) : DatabaseReference
    fun deleteUserPreference(contex:Context)
    fun getUserPreferenceDto(contex: Context) : UserDto
}