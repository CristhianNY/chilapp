package com.cristhianbonilla.com.chilapp.domain.contrats.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
import com.cristhianbonilla.com.chilapp.ui.fragments.home.FriendsAdapterRecyclerView

interface HomeListenerDomain{

    fun onFriendsRead(contacts: List<ContactDto>,
                      friendsAdapterRecyclerView:FriendsAdapterRecyclerView
    )

    fun getFriends(context: Context, root: RecyclerView?,friendsAdapterRecyclerView: FriendsAdapterRecyclerView)
}