package com.cristhianbonilla.com.domain.repositories.login.repositories.features.dashboard.repository

import android.content.Context
import com.cristhianbonilla.com.domain.dtos.UserDto
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.base.BaseRepository

class DashBoardRepository : BaseRepository(), DashBoardRepositoryInterface{

    override fun saveSecretPost(context: Context, userDto: UserDto?) {

    }
}