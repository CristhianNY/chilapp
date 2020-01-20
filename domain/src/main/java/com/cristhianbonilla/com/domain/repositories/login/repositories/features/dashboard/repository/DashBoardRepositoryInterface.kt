package com.cristhianbonilla.com.domain.repositories.login.repositories.features.dashboard.repository

import android.content.Context
import com.cristhianbonilla.com.domain.dtos.ContactDto
import com.cristhianbonilla.com.domain.dtos.UserDto

interface DashBoardRepositoryInterface{
    fun saveSecretPost(context: Context, userDto: UserDto?)
}