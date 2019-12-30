package com.cristhianbonilla.com.domain.repositories.login

import com.cristhianbonilla.com.domain.dtos.UserDto
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.login.LoginRepository

class LoginDomain(loginRepository : LoginRepository){

    var loginRepository : LoginRepository = loginRepository

    fun saveUser(user:UserDto){

        loginRepository.saveUser(user)

    }

}
