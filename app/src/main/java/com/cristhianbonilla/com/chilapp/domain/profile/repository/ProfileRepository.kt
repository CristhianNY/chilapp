package com.cristhianbonilla.com.chilapp.domain.profile.repository

import android.content.Context
import android.util.Log
import android.view.View
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.domain.base.BaseRepository
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerDomain
import com.cristhianbonilla.com.chilapp.domain.contrats.profile.ProfileListenerDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class ProfileRepository  @Inject constructor(listenerDomain:ProfileListenerDomain): BaseRepository() , ProfileRepositoryInterface {

    var listenerDomainmio: ProfileListenerDomain

    init {

        //App.instance.getComponent().inject(this)

        listenerDomainmio = listenerDomain
    }

    override fun getUserInformation(context: Context, userDto: UserDto?,root:View) {
        getFirebaseInstance().child("User/${userDto?.userId}").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var user = dataSnapshot.getValue(UserDto::class.java)

                listenerDomainmio.userInformatinRead(user,root)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("onCancelled", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

}