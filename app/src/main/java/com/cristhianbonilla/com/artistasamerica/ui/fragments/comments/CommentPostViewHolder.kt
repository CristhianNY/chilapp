package com.cristhianbonilla.com.artistasamerica.ui.fragments.comments

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.artistasamerica.domain.contrats.comments.RecyclerCommentsPostListener
import com.cristhianbonilla.com.artistasamerica.domain.dtos.CommentPostDto
import kotlinx.android.synthetic.main.item_comments.view.*


class CommentPostViewHolder constructor(
    itemview: View
): RecyclerView.ViewHolder(itemview){

    val ownerAnonymous : TextView = itemview.owner_anonymous
    val secretPostMessage : TextView = itemview.comment_post
    val commentCardView : CardView = itemview.comment_card
    fun bind(
        commentPost: CommentPostDto,
        listener: RecyclerCommentsPostListener,
        position: Int
    ){

        var lastPosition = -1

        if(position> lastPosition){
           // commentCardView.animation = AnimationUtils.loadAnimation(App.instance.applicationContext, R.anim.fade_transation_animation)
            lastPosition = position
        }
        secretPostMessage.setText(commentPost.comment)

        ownerAnonymous.setOnClickListener(View.OnClickListener {

            listener.itemCliekc(itemView,position, commentPost )

        })
    }

}