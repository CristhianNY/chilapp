package com.cristhianbonilla.com.artistasamerica.ui.fragments.dashboard

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.artistasamerica.domain.dtos.SecretPost
import kotlinx.android.synthetic.main.counter_panel.view.*

class SecretPostViewHolder constructor(
    itemview:View
): RecyclerView.ViewHolder(itemview){


    private val commentImageButton : ImageView = itemview.commentsCountImageView
    private val likesImageView : ImageView = itemview.likesImageView
    private val dislikeImageView : ImageView = itemview.dislike

    fun bind(
        secretPost: SecretPost,
        listener: RecyclerpostListener,
        position: Int
    ){

        listener.printElement(secretPost,position,itemView)
        commentImageButton.setOnClickListener(View.OnClickListener {
            listener.itemCliekc(itemView,position, secretPost )
        })

        dislikeImageView.setOnClickListener {
            listener.btnDisLike(itemView,position,secretPost)
        }

        likesImageView.setOnClickListener {
            listener.btnLike(itemView,position,secretPost)

        }
    }

}