package com.cristhianbonilla.com.chilapp.domain.contrats.profile

import android.content.Context
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto

interface ProfileFragmentListerner {
    fun onUserInformatinRead(userDto: UserDto?)
}