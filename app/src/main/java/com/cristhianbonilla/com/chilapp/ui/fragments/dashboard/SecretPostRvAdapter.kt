package com.cristhianbonilla.com.chilapp.ui.fragments.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.domain.dtos.SecretPost

class SecretPostRvAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

        when(holder){
            is SecretPostViewHolder->{
                holder.bind(secrePostItem.get(position))
            }
        }
    }
}