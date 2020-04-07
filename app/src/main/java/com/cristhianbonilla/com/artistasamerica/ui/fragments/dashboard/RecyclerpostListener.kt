package com.cristhianbonilla.com.artistasamerica.ui.fragments.dashboard

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.artistasamerica.domain.dtos.SecretPost

interface RecyclerpostListener{

    fun itemCliekc(
        view: View,
        position: Int,
        secretPost: SecretPost
    )

    fun btnLike( view: View,
                 position: Int,
                 secretPost: SecretPost)

    fun btnDisLike( view: View,
                 position: Int,
                 secretPost: SecretPost)

    fun positionListener(
        view: RecyclerView,
        position: Int
    )

    fun printElement(secretPost: SecretPost , position: Int, itemView:View)

}