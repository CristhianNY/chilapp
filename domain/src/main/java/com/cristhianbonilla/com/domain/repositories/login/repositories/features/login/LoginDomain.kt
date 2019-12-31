package com.cristhianbonilla.com.domain.repositories.login.repositories.features.login

import android.content.Context
import com.cristhianbonilla.com.domain.dtos.UserDto
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.login.repository.LoginRepository

class LoginDomain(loginRepository : LoginRepository){

    var loginRepository : LoginRepository = loginRepository

    fun saveUser(user:UserDto , contex:Context){

        loginRepository.saveUser(user, contex)
    }

    fun saveUserPreference(user:UserDto , contex:Context){
        loginRepository.saveUserPreference(user, contex)
    }

    fun getUserPreference(key:String , contex:Context){
        loginRepository.getUserPreference(key, contex)
    }

    fun getUserNamePreference(key:String , contex:Context) : String?{
        return loginRepository.getUserNamePreference(key, contex)
    }

    fun deleteeUserPreference(key:String , contex:Context){
        loginRepository.deleteUserPreference(key, contex)
    }


}
