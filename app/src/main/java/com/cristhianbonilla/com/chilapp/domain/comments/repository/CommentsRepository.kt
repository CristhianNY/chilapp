package com.cristhianbonilla.com.chilapp.domain.comments.repository

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.domain.base.BaseRepository
import com.cristhianbonilla.com.chilapp.domain.contrats.comments.ListenerCommentsDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.CommentPostDto
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.fragments.comments.CommentsPostAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class CommentsRepository  @Inject constructor(listenerDomain: ListenerCommentsDomain) : BaseRepository(), CommentsRepositortyInterface{

    var listenerCommentsDomain: ListenerCommentsDomain
    init {
        App.instance.getComponent().inject(this)
        listenerCommentsDomain = listenerDomain
    }

    override fun readComments(
        userDto: UserDto?,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter,
        idSecretPost: String?
    ) {
        val commentpostList = ArrayList<CommentPostDto>()
        getFirebaseInstance().child("commentsPost/$idSecretPost").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                commentpostList.clear()
                for (postSnapshot in dataSnapshot.children) {

                    var sercretPost = postSnapshot.getValue(CommentPostDto::class.java)
                    sercretPost?.let { commentpostList.add(it) }
                    listenerCommentsDomain.onCommentsPostRead(commentpostList,userDto,root,commentsPostAdapter)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("onCancelled", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    override fun saveCommentPost(
        contex: Context,
        message: String,
        user: UserDto,
        idSecretPost: String?
    ) {

        val Key: String? =
            idSecretPost?.let { getFirebaseInstance().child("commentsPost").child(it).push().getKey() }
        var commentPost = Key?.let {
            CommentPostDto(
                message,
                user!!.userId,
                "",
                it
            )
        }

        getFirebaseInstance().child("commentsPost").child(idSecretPost+"/"+Key!!).setValue(commentPost)

    }

}