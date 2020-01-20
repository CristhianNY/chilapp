package com.cristhianbonilla.com.domain.repositories.login.repositories.features.home

import android.content.Context
import com.cristhianbonilla.com.domain.dtos.UserDto
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.home.repository.HomeRepository
import io.reactivex.Completable
import java.lang.Exception
import java.util.concurrent.TimeUnit

class HomeDomain(homerepository: HomeRepository){
    var homeRepository : HomeRepository = homerepository

    fun saveContactsPhoneIntoFirebase(context: Context, userPhonenumber: UserDto?){
        homeRepository.saveContactsPhoneIntoFirebase(context,userPhonenumber)
    }

    fun getFriendsPost(){


    }


}