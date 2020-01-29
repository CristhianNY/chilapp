package com.cristhianbonilla.com.chilapp.domain.contrats.dashboard

import android.content.Context
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto

interface ListenerDomain{

    fun onReadSecretPost(secretpostList: ArrayList<SecretPost>)
    fun saveSecretPost(contex: Context, message: String, user: UserDto)
    fun getSecretsPost(user: UserDto?)
}