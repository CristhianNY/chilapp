package com.cristhianbonilla.com.domain.repositories.login.repositories.features.login.repository

import android.content.Context
import com.cristhianbonilla.com.domain.dtos.UserDto

interface LoginInterfaceRepository {

    fun saveUser(user:UserDto, contex:Context)
    fun saveUserPreference(user:UserDto, contex:Context)
    fun getUserPreference(key:String, contex:Context)
    fun getUserNamePreference(key:String, contex:Context) : String?
    fun deleteUserPreference(key:String, contex:Context)
}