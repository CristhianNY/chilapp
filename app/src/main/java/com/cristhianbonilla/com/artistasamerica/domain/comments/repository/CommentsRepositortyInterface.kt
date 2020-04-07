package com.cristhianbonilla.com.artistasamerica.domain.comments.repository

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.artistasamerica.domain.dtos.UserDto
import com.cristhianbonilla.com.artistasamerica.ui.fragments.comments.CommentsPostAdapter

interface CommentsRepositortyInterface{

    fun readComments(
        userDto: UserDto?,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter,
        idSecretPost: String?
    )

    fun saveCommentPost(contex: Context, message: String, user: UserDto, idSecretPost: String?)
}