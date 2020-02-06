package com.cristhianbonilla.com.chilapp.domain.contrats.dashboard

import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.SecretPostRvAdapter

interface ListenerActivity{
    fun onSecretPostRead(
        secretpostArrayList: ArrayList<SecretPost>,
        root: RecyclerView?,
        secretPostRvAdapter: SecretPostRvAdapter
    )
}