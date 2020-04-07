package com.cristhianbonilla.com.artistasamerica.ui.fragments.home

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.artistasamerica.domain.dtos.ContactDto
import kotlinx.android.synthetic.main.item_friends.view.*

class FriendContactViewHolder constructor(
    itemview: View
): RecyclerView.ViewHolder(itemview){

    val friendName : TextView = itemview.friend_name
    val profileImage : ImageView = itemview.rounded_iv_profile
    val btnInviteFriends : Button = itemview.btn_invite_friends

    fun bind(
        contact: ContactDto,
        listener: RecyclerFriendListener,
        position: Int
    ){

        friendName.setText(contact.name)

        profileImage.setOnClickListener(View.OnClickListener {

            listener.itemCliekc(itemView,position, contact )

        })

        btnInviteFriends.setOnClickListener {
            listener.inviteFriends(itemView,position,contact)
        }
    }

}