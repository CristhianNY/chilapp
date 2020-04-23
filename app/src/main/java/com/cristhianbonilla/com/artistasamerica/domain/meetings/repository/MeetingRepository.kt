package com.cristhianbonilla.com.artistasamerica.domain.meetings.repository

import android.content.Context
import com.cristhianbonilla.com.artistasamerica.domain.base.BaseRepository
import com.cristhianbonilla.com.artistasamerica.domain.dtos.MeetingDto
import com.google.firebase.database.DatabaseReference

class MeetingRepository : MeetingRepositoryInterface, BaseRepository() {


    override fun getMeetingScheduledByUserId(userId: String): DatabaseReference {

        return getFirebaseInstance().child("meetings/${userId}")
    }

    override fun saveMeeting(
        userId: String,
        title: String,
        name: String,
        phone: String,
        idEventos: String,
        password: String,
        date: String,
        duration: String
    ) {
        val Key: String? =   getFirebaseInstance().child("meetings").child(userId).push().getKey()
        var MeetingDto = Key?.let {
            MeetingDto(
                userId,
                title,
                name,
                phone,
                idEventos,
                password,
                date,
                duration
            )
        }

        getFirebaseInstance().child("meetings").child(userId).setValue(MeetingDto)
    }


}