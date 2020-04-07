package com.cristhianbonilla.com.artistasamerica.domain.contrats.comments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.artistasamerica.domain.dtos.CommentPostDto

interface RecyclerCommentsPostListener{

    fun itemCliekc(
        view: View,
        position: Int,
        comment: CommentPostDto
    )

    fun positionListener(
        view: RecyclerView,
        position: Int
    )

}