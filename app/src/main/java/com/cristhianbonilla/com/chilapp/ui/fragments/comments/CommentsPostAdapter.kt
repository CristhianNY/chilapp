package com.cristhianbonilla.com.chilapp.ui.fragments.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.domain.contrats.comments.RecyclerCommentsPostListener
import com.cristhianbonilla.com.chilapp.domain.dtos.CommentPostDto

class CommentsPostAdapter(
    listener: RecyclerCommentsPostListener,
    recyclerViewElement: RecyclerView?
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var recyclerpostListener: RecyclerCommentsPostListener
    var recyclerView: RecyclerView
    lateinit var context: FragmentActivity

    init {
        recyclerpostListener = listener

        recyclerView = recyclerViewElement!!
    }

    private var commentItems : List<CommentPostDto> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return  CommentPostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comments,parent,false))

    }

    override fun getItemCount(): Int {
        return commentItems.size
    }

    fun submitList(secretPostList: List<CommentPostDto>){

        commentItems = secretPostList
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        recyclerpostListener.positionListener(recyclerView,position)

        when(holder){
            is CommentPostViewHolder ->{
                holder.bind(commentItems.get(position), recyclerpostListener, position)
            }

        }
    }
}