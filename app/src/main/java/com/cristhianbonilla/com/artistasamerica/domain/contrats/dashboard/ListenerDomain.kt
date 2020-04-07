package com.cristhianbonilla.com.artistasamerica.domain.contrats.dashboard

import android.content.Context
import com.cristhianbonilla.com.artistasamerica.domain.dtos.UserDto

interface ListenerDomain{
    fun saveSecretPost(
        contex: Context,
        message: String,
        user: UserDto,
        colorPost: String
    )
}