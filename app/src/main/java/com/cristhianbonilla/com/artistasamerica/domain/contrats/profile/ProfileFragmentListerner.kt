package com.cristhianbonilla.com.artistasamerica.domain.contrats.profile

import android.view.View
import com.cristhianbonilla.com.artistasamerica.domain.dtos.UserDto

interface ProfileFragmentListerner {
    fun onUserInformatinRead(
        userDto: UserDto?,
        root: View
    )
}