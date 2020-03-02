package com.cristhianbonilla.com.chilapp.domain.home

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.domain.contrats.home.HomeListenerDomain
import com.cristhianbonilla.com.chilapp.domain.contrats.home.ListenerHomeFragment
import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.domain.home.repository.HomeRepository
import com.cristhianbonilla.com.chilapp.ui.fragments.home.FriendsAdapterRecyclerView
import com.cristhianbonilla.com.chilapp.ui.fragments.home.RecyclerFriendListener
import javax.inject.Inject

class HomeDomain  @Inject constructor(listenerActivity: ListenerHomeFragment) : HomeListenerDomain{

    var listenerHomeFragment: ListenerHomeFragment

    var homeRepository : HomeRepository
    lateinit var contactos: List<ContactDto>

    init {
        App.instance.getComponent().inject(this)

        listenerHomeFragment = listenerActivity

        homeRepository = HomeRepository(this)
    }


    fun saveContactsPhoneIntoFirebase(context: Context, userPhonenumber: UserDto?){
        homeRepository.saveContactsPhoneIntoFirebase(context,userPhonenumber)
    }

    override fun onFriendsRead(
        contacts: MutableList<ContactDto>,
        friendsAdapterRecyclerView: FriendsAdapterRecyclerView
    ) {
        listenerHomeFragment.onFriensdRead(contacts,friendsAdapterRecyclerView)
    }

    override fun getFriends(context: Context, root: RecyclerView?,friendsAdapterRecyclerView: FriendsAdapterRecyclerView) {
        homeRepository.getFriendsByUser(context,root,friendsAdapterRecyclerView)
    }

     fun shareAppWithFriends(context: Context){
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = "Te invito a usar esta app para que te enteres de secretos "
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }


}