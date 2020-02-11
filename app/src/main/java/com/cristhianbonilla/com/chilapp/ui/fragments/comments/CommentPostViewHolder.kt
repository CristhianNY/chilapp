package com.cristhianbonilla.com.chilapp.ui.fragments.comments

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.contrats.comments.RecyclerCommentsPostListener
import com.cristhianbonilla.com.chilapp.domain.dtos.CommentPostDto
import kotlinx.android.synthetic.main.item_comments.view.*
import kotlinx.android.synthetic.main.item_secret_post.view.*
import kotlinx.android.synthetic.main.item_secret_post.view.owner_anonymous

class CommentPostViewHolder constructor(
    itemview: View
): RecyclerView.ViewHolder(itemview){

    val ownerAnonymous : TextView = itemview.owner_anonymous
    val secretPostMessage : TextView = itemview.comment_post

    fun bind(
        commentPost: CommentPostDto,
        listener: RecyclerCommentsPostListener,
        position: Int
    ){
        ownerAnonymous.setText("Todos los post son anonimos")
        secretPostMessage.setText("por ahora poner")

        ownerAnonymous.setOnClickListener(View.OnClickListener {

            listener.itemCliekc(itemView,position, commentPost )

        })
    }

}