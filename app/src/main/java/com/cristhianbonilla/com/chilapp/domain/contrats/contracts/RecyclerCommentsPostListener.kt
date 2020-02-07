package com.cristhianbonilla.com.chilapp.domain.contrats.contracts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.CommentPostDto

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