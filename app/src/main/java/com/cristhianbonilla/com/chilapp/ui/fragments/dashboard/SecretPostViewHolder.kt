package com.cristhianbonilla.com.chilapp.ui.fragments.dashboard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import kotlinx.android.synthetic.main.counter_panel.view.*
import kotlinx.android.synthetic.main.item_secret_post.view.*

class SecretPostViewHolder constructor(
    itemview:View
): RecyclerView.ViewHolder(itemview){

    val ownerAnonymous :TextView = itemview.owner_anonymous
    val secretPostMessage :TextView = itemview.secret_post_message
    val commentImageButton : ImageView = itemview.commentsCountImageView

    fun bind(
        secretPost: SecretPost,
        listener: RecyclerpostListener,
        position: Int
    ){
        ownerAnonymous.setText("Todos los post son anonimos")
        secretPostMessage.setText(secretPost.message)

        commentImageButton.setOnClickListener(View.OnClickListener {

            listener.itemCliekc(itemView,position, secretPost )

        })
    }

}