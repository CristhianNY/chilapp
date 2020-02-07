package com.cristhianbonilla.com.chilapp.domain.contrats.contracts

import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.fragments.comments.CommentsPostAdapter
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.SecretPostRvAdapter

interface ListenerCommentsDomain{

    fun getCommentsPost(
        user: UserDto?,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter
    )

}