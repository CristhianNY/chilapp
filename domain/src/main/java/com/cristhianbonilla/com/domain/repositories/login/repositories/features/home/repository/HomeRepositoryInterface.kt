package com.cristhianbonilla.com.domain.repositories.login.repositories.features.home.repository

import android.content.Context

interface HomeRepositoryInterface{
    fun saveContactsPhoneIntoFirebase(context: Context)
}