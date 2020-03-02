package com.cristhianbonilla.com.chilapp.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto

class FriendsAdapterRecyclerView(
    listener: RecyclerFriendListener,
    recyclerViewElement: RecyclerView?
): RecyclerView.Adapter<RecyclerView.ViewHolder>() , Filterable {


    var recyclerpostListener: RecyclerFriendListener
    var recyclerView: RecyclerView
    lateinit var context: FragmentActivity

    companion object{
        private var contantcItem : MutableList<ContactDto> = ArrayList()
        private var myContacts : MutableList<ContactDto> = ArrayList()
    }

    init {
        recyclerpostListener = listener
        recyclerView = recyclerViewElement!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  FriendContactViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_friends,parent,false))

    }

    override fun getItemCount(): Int {
        return contantcItem.size
    }

    fun submitList(contacts: MutableList<ContactDto>){
        contantcItem = contacts
        myContacts = contacts
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        recyclerpostListener.positionListener(recyclerView,position)

        when(holder){
            is FriendContactViewHolder ->{
                holder.bind(contantcItem.get(position), recyclerpostListener, position)
            }

        }
    }

    override fun getFilter(): Filter {
        return object:Filter(){
            override fun performFiltering(charString: CharSequence?): FilterResults {
                val charSearch:String = charString.toString()
                if(!charSearch.isEmpty()){
                    val resultContactsList = ArrayList<ContactDto>()
                    for(row:ContactDto in contantcItem){
                        if(row.name!!.toLowerCase().contains(charSearch.toLowerCase())){
                            resultContactsList.add(row)
                        }
                    }

                    contantcItem = resultContactsList
                }else{
                    contantcItem = myContacts
                }
                val filterResults:FilterResults = Filter.FilterResults()
                filterResults.values = contantcItem

                return  filterResults
            }

            override fun publishResults(chartString: CharSequence?, results: FilterResults?) {
                contantcItem = results?.values as MutableList<ContactDto>
               recyclerView.adapter!!.notifyDataSetChanged()
            }

        }
    }
}