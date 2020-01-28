package com.cristhianbonilla.com.chilapp.domain.home

import android.content.Context
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.domain.home.repository.HomeRepository

class HomeDomain(homerepository: HomeRepository){
    var homeRepository : HomeRepository = homerepository

    fun saveContactsPhoneIntoFirebase(context: Context, userPhonenumber: UserDto?){
        homeRepository.saveContactsPhoneIntoFirebase(context,userPhonenumber)
    }

    fun getFriendsPost(){


    }


}