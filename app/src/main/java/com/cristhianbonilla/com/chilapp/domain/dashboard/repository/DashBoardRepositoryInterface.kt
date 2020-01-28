package com.cristhianbonilla.com.chilapp.domain.dashboard.repository

import android.content.Context
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto

interface DashBoardRepositoryInterface{
    fun saveSecretPost(context: Context, message: String , userDto: UserDto?)
}