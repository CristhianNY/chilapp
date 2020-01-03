package com.cristhianbonilla.com.domain.repositories.login.repositories.features.base

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

open class BaseRepository{
   protected fun getFirebaseInstance() : DatabaseReference{
       return FirebaseDatabase.getInstance().reference
    }
}