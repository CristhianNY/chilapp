package com.cristhianbonilla.com.chilapp.ui.fragments.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost

interface RecyclerFriendListener{

    fun itemCliekc(
        view: View,
        position: Int,
        contact: ContactDto
    )

    fun positionListener(
        view: RecyclerView,
        position: Int
    )
}