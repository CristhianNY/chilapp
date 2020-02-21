package com.cristhianbonilla.com.chilapp.ui.fragments.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
import kotlinx.android.synthetic.main.counter_panel.view.*
import kotlinx.android.synthetic.main.item_friends.view.*
import kotlinx.android.synthetic.main.item_secret_post.view.*

class FriendContactViewHolder constructor(
    itemview: View
): RecyclerView.ViewHolder(itemview){

    val friendName : TextView = itemview.friend_name
    val profileImage : ImageView = itemview.rounded_iv_profile

    fun bind(
        contact: ContactDto,
        listener: RecyclerFriendListener,
        position: Int
    ){

        friendName.setText(contact.name)

        profileImage.setOnClickListener(View.OnClickListener {

            listener.itemCliekc(itemView,position, contact )

        })
    }

}