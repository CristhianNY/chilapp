package com.cristhianbonilla.com.domain.repositories.login.repositories.features.dashboard

import android.content.Context
import android.text.Editable
import com.cristhianbonilla.com.domain.dtos.UserDto
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.dashboard.repository.DashBoardRepository

class DashBoardDomain (dashBoardRepository: DashBoardRepository){

    var dashBoardRepository = dashBoardRepository

     fun saveSecretPost(contex : Context, message: String, user:UserDto) {

       dashBoardRepository.saveSecretPost(contex,  message, user)
    }
}