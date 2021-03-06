package com.cristhianbonilla.com.chilapp.domain.home.repository

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.fragments.home.FriendsAdapterRecyclerView

interface HomeRepositoryInterface{
    fun saveContactsPhoneIntoFirebase(context: Context , userDto: UserDto?)
    fun saveContactInFirebase(contactList: MutableList<ContactDto>, userDto: UserDto?)
    fun getFriendsByUser(context: Context, root: RecyclerView?, friendsAdapterRecyclerView: FriendsAdapterRecyclerView) : List<ContactDto>
}