package com.cristhianbonilla.com.domain.repositories.login.repositories.features.home

import android.content.Context
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.home.repository.HomeRepository

class HomeDomain(homerepository: HomeRepository){
    var homeRepository : HomeRepository = homerepository

    fun saveContactsPhoneIntoFirebase(context: Context){

        homeRepository.saveContactsPhoneIntoFirebase(context)

    }

}