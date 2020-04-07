package com.cristhianbonilla.com.artistasamerica.ui.activities.meeting

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cristhianbonilla.com.artistasamerica.domain.dtos.UserDto
import com.cristhianbonilla.com.artistasamerica.domain.login.repository.LoginRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MeetingViewModel : ViewModel() {

    var userLive: MutableLiveData<UserDto> = MutableLiveData()
    var loginRepositoryModel: LoginRepository

    init {
        loginRepositoryModel = LoginRepository()
    }
    suspend fun getUserFromFirebase(userDto: UserDto){

        loginRepositoryModel.getUserFromFirebase(userDto).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    userLive.value  = postSnapshot.getValue(UserDto::class.java)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("onCancelled", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
}