package com.cristhianbonilla.com.chilapp.domain.home.repository

import android.content.Context
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto

interface HomeRepositoryInterface{
    fun saveContactsPhoneIntoFirebase(context: Context , userDto: UserDto?)
    fun saveContactInFirebase(contactList: MutableList<ContactDto>, userDto: UserDto?)
}