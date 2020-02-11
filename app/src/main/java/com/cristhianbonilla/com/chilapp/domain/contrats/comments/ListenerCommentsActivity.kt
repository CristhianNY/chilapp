package com.cristhianbonilla.com.chilapp.domain.contrats.comments

import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.CommentPostDto
import com.cristhianbonilla.com.chilapp.ui.fragments.comments.CommentsPostAdapter

interface ListenerCommentsActivity{

    fun onReadCommentsPost(
        secreCommentsPost: ArrayList<CommentPostDto>,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter
    )
}
