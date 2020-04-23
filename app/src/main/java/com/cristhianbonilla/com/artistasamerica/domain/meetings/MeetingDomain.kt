package com.cristhianbonilla.com.artistasamerica.domain.meetings

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cristhianbonilla.com.artistasamerica.App
import com.cristhianbonilla.com.artistasamerica.domain.dtos.MeetingDto
import com.cristhianbonilla.com.artistasamerica.domain.meetings.repository.MeetingRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MeetingDomain : ViewModel() {

    var meetingRepository: MeetingRepository

    var meetingList: MutableLiveData<List<MeetingDto>> = MutableLiveData()

    init {
        App.instance.getComponent().inject(this)
        meetingRepository = MeetingRepository()
    }

    suspend fun getAllSecrets(userId: String) {
        val meetingsList = ArrayList<MeetingDto>()
        meetingRepository.getMeetingScheduledByUserId(userId).limitToLast(100)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    meetingsList.clear()
                    for (postSnapshot in dataSnapshot.children) {

                        var meeting = postSnapshot.getValue(MeetingDto::class.java)
                        meeting?.let { meetingsList.add(it) }

                        meetingList.value = meetingsList
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("onCancelled", "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            })
    }

    suspend fun saveMeeting(
        userId: String,
        name: String,
        phone: String,
        title: String,
        idEventos: String,
        password: String,
        date: String,
        duration: String
    ) {
        meetingRepository.saveMeeting(
            userId,
            name,
            phone,
            title, idEventos,
            password,
            date,
            duration
        )
    }
}