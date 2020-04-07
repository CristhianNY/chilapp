package com.cristhianbonilla.com.artistasamerica.domain.comments

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.artistasamerica.App
import com.cristhianbonilla.com.artistasamerica.domain.comments.repository.CommentsRepository
import com.cristhianbonilla.com.artistasamerica.domain.contrats.comments.ListenerCommentsActivity
import com.cristhianbonilla.com.artistasamerica.domain.contrats.comments.ListenerCommentsDomain
import com.cristhianbonilla.com.artistasamerica.domain.dtos.CommentPostDto
import com.cristhianbonilla.com.artistasamerica.domain.dtos.UserDto
import com.cristhianbonilla.com.artistasamerica.ui.fragments.comments.CommentsPostAdapter
import javax.inject.Inject

class CommentsDomain  @Inject constructor(listenerActivity: ListenerCommentsActivity) : ListenerCommentsDomain {

    var listenerCommentsActivity: ListenerCommentsActivity

    var commentsRepository : CommentsRepository

    init {
        App.instance.getComponent().inject(this)

        listenerCommentsActivity = listenerActivity

        commentsRepository = CommentsRepository(this)
    }

    override fun getCommentsPost(
        user: UserDto?,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter,
        idCommentPost: String?
    ) {
        commentsRepository.readComments(user,root,commentsPostAdapter,idCommentPost)
    }

    override fun saveCommentPost(
        contex: Context,
        message: String,
        user: UserDto,
        idCommentPost: String?
    ) {

        commentsRepository.saveCommentPost(contex,message,user,idCommentPost)

    }

    override fun onCommentsPostRead(
        commentsPostList: ArrayList<CommentPostDto>,
        user: UserDto?,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter
    ) {
        listenerCommentsActivity.onReadCommentsPost(commentsPostList,root,commentsPostAdapter)
    }


}