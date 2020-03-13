package com.cristhianbonilla.com.chilapp.ui.fragments.dashboard

import android.view.View
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import kotlinx.android.synthetic.main.counter_panel.view.*
import kotlinx.android.synthetic.main.item_secret_post.view.*

class SecretPostViewHolder constructor(
    itemview:View
): RecyclerView.ViewHolder(itemview){


    private val commentImageButton : ImageView = itemview.commentsCountImageView
    private val likesImageView : ImageView = itemview.likesImageView

    fun bind(
        secretPost: SecretPost,
        listener: RecyclerpostListener,
        position: Int
    ){

        listener.printElement(secretPost,position,itemView)
        commentImageButton.setOnClickListener(View.OnClickListener {
            listener.itemCliekc(itemView,position, secretPost )
        })

        likesImageView.setOnClickListener {
            listener.btnLike(itemView,position,secretPost)

        }
    }

}