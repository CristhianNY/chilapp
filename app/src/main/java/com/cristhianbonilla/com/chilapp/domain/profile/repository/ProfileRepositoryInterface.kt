package com.cristhianbonilla.com.chilapp.domain.profile.repository

import android.content.Context
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto

interface ProfileRepositoryInterface {
    fun getUserInformation(context: Context, userDto: UserDto?)
}