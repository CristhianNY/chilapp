package com.cristhianbonilla.com.chilapp.ui.fragments.dashboard

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost

interface RecyclerpostListener{

    fun itemCliekc(
        view: View,
        position: Int,
        secretPost: SecretPost
    )

    fun positionListener(
        view: RecyclerView,
        position: Int
    )

}