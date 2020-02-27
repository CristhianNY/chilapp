package com.cristhianbonilla.com.chilapp.domain.contrats.home

import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.SecretPostRvAdapter
import com.cristhianbonilla.com.chilapp.ui.fragments.home.FriendsAdapterRecyclerView

interface ListenerHomeFragment{
    fun onFriensdRead(
        contacts: List<ContactDto>,
        friendsAdapterRecyclerView: FriendsAdapterRecyclerView
    )
}