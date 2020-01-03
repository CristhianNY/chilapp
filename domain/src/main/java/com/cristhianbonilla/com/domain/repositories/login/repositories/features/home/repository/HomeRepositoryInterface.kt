package com.cristhianbonilla.com.domain.repositories.login.repositories.features.home.repository

import android.content.Context
import com.cristhianbonilla.com.domain.dtos.ContactDto
import com.cristhianbonilla.com.domain.dtos.UserDto

interface HomeRepositoryInterface{
    fun saveContactsPhoneIntoFirebase(context: Context , userDto: UserDto?)
    fun saveContactInFirebase(contactList: MutableList<ContactDto>, userDto: UserDto?)
}