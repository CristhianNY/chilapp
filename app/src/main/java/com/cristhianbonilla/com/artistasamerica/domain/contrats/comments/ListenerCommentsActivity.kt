package com.cristhianbonilla.com.artistasamerica.domain.contrats.comments

import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.artistasamerica.domain.dtos.CommentPostDto
import com.cristhianbonilla.com.artistasamerica.ui.fragments.comments.CommentsPostAdapter

interface ListenerCommentsActivity{

    fun onReadCommentsPost(
        secreCommentsPost: ArrayList<CommentPostDto>,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter
    )
}
