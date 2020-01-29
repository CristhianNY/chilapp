package com.cristhianbonilla.com.chilapp.domain.contrats.dashboard

import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost

interface ListenerActivity{
    fun onSecretPostRead(secretpostArrayList:ArrayList<SecretPost>)
}