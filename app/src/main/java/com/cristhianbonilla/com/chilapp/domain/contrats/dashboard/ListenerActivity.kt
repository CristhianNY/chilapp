package com.cristhianbonilla.com.chilapp.domain.contrats.dashboard

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost

interface ListenerActivity{
    fun onSecretPostRead(secretpostArrayList:ArrayList<SecretPost>, root: RecyclerView?)
}