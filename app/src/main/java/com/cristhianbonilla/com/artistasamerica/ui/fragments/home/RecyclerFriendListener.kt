package com.cristhianbonilla.com.artistasamerica.ui.fragments.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.artistasamerica.domain.dtos.ContactDto

interface RecyclerFriendListener{

    fun itemCliekc(
        view: View,
        position: Int,
        contact: ContactDto
    )

    fun inviteFriends(view: View,
                      position: Int,
                      contact: ContactDto)

    fun positionListener(
        view: RecyclerView,
        position: Int
    )
}