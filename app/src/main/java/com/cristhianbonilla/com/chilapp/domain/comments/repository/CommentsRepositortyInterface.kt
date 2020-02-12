package com.cristhianbonilla.com.chilapp.domain.comments.repository

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.fragments.comments.CommentsPostAdapter
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.SecretPostRvAdapter

interface CommentsRepositortyInterface{

    fun readComments(
        userDto: UserDto?,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter,
        idSecretPost: String?
    )

    fun saveCommentPost(contex: Context, message: String, user: UserDto, idSecretPost: String?)
}