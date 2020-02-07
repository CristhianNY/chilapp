package com.cristhianbonilla.com.chilapp.domain.contrats.contracts

import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.CommentPostDto
import com.cristhianbonilla.com.chilapp.ui.fragments.comments.CommentsPostAdapter
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.SecretPostRvAdapter

interface ListenerCommentsActivity{

    fun onReadCommentsPost(
        secreCommentsPost: ArrayList<CommentPostDto>,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter
    )
}
