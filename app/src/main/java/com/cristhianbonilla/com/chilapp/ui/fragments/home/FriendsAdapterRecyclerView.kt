package com.cristhianbonilla.com.chilapp.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto

class FriendsAdapterRecyclerView(
    listener: RecyclerFriendListener,
    recyclerViewElement: RecyclerView?
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var recyclerpostListener: RecyclerFriendListener
    var recyclerView: RecyclerView
    lateinit var context: FragmentActivity

    init {
        recyclerpostListener = listener

        recyclerView = recyclerViewElement!!
    }

    private var contantcItem : List<ContactDto> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return  FriendContactViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_friends,parent,false))

    }

    override fun getItemCount(): Int {
        return contantcItem.size
    }

    fun submitList(contacts: List<ContactDto>){

        contantcItem = contacts
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        recyclerpostListener.positionListener(recyclerView,position)

        when(holder){
            is FriendContactViewHolder ->{
                holder.bind(contantcItem.get(position), recyclerpostListener, position)
            }

        }
    }
}