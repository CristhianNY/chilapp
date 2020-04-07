package com.cristhianbonilla.com.artistasamerica.domain.contrats.home

import com.cristhianbonilla.com.artistasamerica.domain.dtos.ContactDto
import com.cristhianbonilla.com.artistasamerica.ui.fragments.home.FriendsAdapterRecyclerView

 interface ListenerHomeFragment{
    var contactList:List<ContactDto>

    fun onFriensdRead(
        contacts: MutableList<ContactDto>,
        friendsAdapterRecyclerView: FriendsAdapterRecyclerView
    )
}