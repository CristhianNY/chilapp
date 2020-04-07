package com.cristhianbonilla.com.artistasamerica.ui.fragments.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.artistasamerica.R
import com.cristhianbonilla.com.artistasamerica.domain.dtos.SecretPost

class SecretPostRvAdapter(
    listener: RecyclerpostListener,
    recyclerViewElement: RecyclerView?
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var recyclerpostListener: RecyclerpostListener
    var recyclerView: RecyclerView
    lateinit var context: FragmentActivity

    init {
        recyclerpostListener = listener

        recyclerView = recyclerViewElement!!
    }

    private var secrePostItem : List<SecretPost> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return  SecretPostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_secret_post,parent,false))

    }

    override fun getItemCount(): Int {
     return secrePostItem.size
    }

    fun submitList(secretPostList: List<SecretPost>){

        secrePostItem = secretPostList
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            recyclerpostListener.positionListener(recyclerView,position)

        when(holder){
            is SecretPostViewHolder->{
                holder.bind(secrePostItem.get(position), recyclerpostListener, position)
            }

        }
    }
}