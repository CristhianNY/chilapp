package com.cristhianbonilla.com.domain.repositories.login.repositories.features.dashboard

import com.cristhianbonilla.com.domain.repositories.login.repositories.features.dashboard.repository.DashBoardRepository

class DashBoardDomain (dashBoardRepository: DashBoardRepository){

    var dashBoardRepository = dashBoardRepository

    public fun saveSecretPost(){

        print("do somethign")
    }
}