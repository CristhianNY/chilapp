package com.cristhianbonilla.com.domain.repositories.login.repositories.features.login

import com.cristhianbonilla.com.domain.dtos.UserDto

interface LoginInterfaceRepository {

    fun saveUser(user:UserDto)
}