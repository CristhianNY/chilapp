package com.cristhianbonilla.com.chilapp.domain.contrats.profile

import android.content.Context
import android.view.View
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto

interface ProfileListenerDomain {

    fun userInformatinRead(userDto: UserDto?, root:View)
}