package com.cristhianbonilla.com.chilapp.domain.contrats.profile

import android.view.View
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto

interface ProfileFragmentListerner {
    fun onUserInformatinRead(
        userDto: UserDto?,
        root: View
    )
}