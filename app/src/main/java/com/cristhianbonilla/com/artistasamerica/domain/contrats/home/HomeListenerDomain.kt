package com.cristhianbonilla.com.artistasamerica.domain.contrats.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.artistasamerica.domain.dtos.ContactDto
import com.cristhianbonilla.com.artistasamerica.ui.fragments.home.FriendsAdapterRecyclerView

interface HomeListenerDomain{

    fun onFriendsRead(contacts: MutableList<ContactDto>,
                      friendsAdapterRecyclerView:FriendsAdapterRecyclerView
    )

    fun getFriends(context: Context, root: RecyclerView?,friendsAdapterRecyclerView: FriendsAdapterRecyclerView)
}