package com.cristhianbonilla.com.chilapp.domain.dashboard

import android.content.Context
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.domain.dashboard.repository.DashBoardRepository

class DashBoardDomain (dashBoardRepository: DashBoardRepository){

    var dashBoardRepository = dashBoardRepository

     fun saveSecretPost(contex : Context, message: String, user: UserDto) {

       dashBoardRepository.saveSecretPost(contex,  message, user)
    }
}