package com.cristhianbonilla.com.artistasamerica.domain.profile.repository

import android.content.Context
import android.view.View
import com.cristhianbonilla.com.artistasamerica.domain.dtos.UserDto

interface ProfileRepositoryInterface {
    fun getUserInformation(context: Context, userDto: UserDto?,root: View)
}