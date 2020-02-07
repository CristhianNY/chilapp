package com.cristhianbonilla.com.chilapp.ui.fragments.comments

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.contrats.contracts.RecyclerCommentsPostListener
import com.cristhianbonilla.com.chilapp.domain.dtos.CommentPostDto
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.RecyclerpostListener
import kotlinx.android.synthetic.main.item_secret_post.view.*

class CommentPostViewHolder constructor(
    itemview: View
): RecyclerView.ViewHolder(itemview){

    val ownerAnonymous : TextView = itemview.owner_anonymous
    val secretPostMessage : TextView = itemview.secret_post_message

    fun bind(
        commentPost: CommentPostDto,
        listener: RecyclerCommentsPostListener,
        position: Int
    ){
        ownerAnonymous.setText("Todos los post son anonimos")
        secretPostMessage.setText(commentPost.comment)

        ownerAnonymous.setOnClickListener(View.OnClickListener {

            listener.itemCliekc(itemView,position, commentPost )

        })
    }

}