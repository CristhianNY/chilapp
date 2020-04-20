package com.cristhianbonilla.com.artistasamerica.domain.meetings.repository

import android.content.Context
import com.google.firebase.database.DatabaseReference

interface MeetingRepositoryInterface {

    fun getMeetingScheduledByUserId(userId: String): DatabaseReference
    fun saveMeeting(
        userId: String,
        context: Context,
        title: String,
        idEventos: String,
        password: String,
        date: String,
        duration: String
    )
}