package com.cristhianbonilla.com.chilapp.domain.comments

import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.contrats.comments.ListenerCommentsActivity
import com.cristhianbonilla.com.chilapp.domain.contrats.comments.ListenerCommentsDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.fragments.comments.CommentsPostAdapter
import javax.inject.Inject

class CommentsDomain  @Inject constructor(listenerActivity: ListenerCommentsActivity) : ListenerCommentsDomain {

    var listenerCommentsActivity: ListenerCommentsActivity

    init {
        listenerCommentsActivity = listenerActivity
    }

    override fun getCommentsPost(
        user: UserDto?,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}