package com.cristhianbonilla.com.chilapp.domain.comments.repository

import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.base.BaseRepository
import com.cristhianbonilla.com.chilapp.domain.contrats.comments.ListenerCommentsDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.fragments.comments.CommentsPostAdapter
import javax.inject.Inject

class CommentsRepository  @Inject constructor(listenerDomain: ListenerCommentsDomain) : BaseRepository(), CommentsRepositortyInterface{

    var listenerCommentsDomain: ListenerCommentsDomain
    init {
        listenerCommentsDomain = listenerDomain
    }

    override fun readComments(
        userDto: UserDto?,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}