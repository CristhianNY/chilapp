package com.cristhianbonilla.com.chilapp.ui.fragments.dashboard

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.domain.dtos.SecretPost
import kotlinx.android.synthetic.main.item_secret_post.view.*

class SecretPostViewHolder constructor(
    itemview:View
): RecyclerView.ViewHolder(itemview){

    val ownerAnonymous :TextView = itemview.owner_anonymous
    val secretPostMessage :TextView = itemview.secret_post_message

    fun bind(secretPost: SecretPost){
        ownerAnonymous.setText(secretPost.owner)
        secretPostMessage.setText(secretPost.message)
    }


}