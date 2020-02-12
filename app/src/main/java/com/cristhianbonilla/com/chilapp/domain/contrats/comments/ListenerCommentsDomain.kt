package com.cristhianbonilla.com.chilapp.domain.contrats.comments

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.CommentPostDto
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.fragments.comments.CommentsPostAdapter

interface ListenerCommentsDomain{

    fun getCommentsPost(
        user: UserDto?,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter,
        idSecretPost: String?
    )

    fun saveCommentPost(contex: Context, message: String, user: UserDto , idSecretPost: String?)

    fun onCommentsPostRead(
        commentsPostList: ArrayList<CommentPostDto>,
        user: UserDto?,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter
    )


}