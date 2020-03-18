package com.cristhianbonilla.com.chilapp.domain.contrats.dashboard

import android.content.Context
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto

interface ListenerDomain{
    fun saveSecretPost(
        contex: Context,
        message: String,
        user: UserDto,
        colorPost: String
    )
}