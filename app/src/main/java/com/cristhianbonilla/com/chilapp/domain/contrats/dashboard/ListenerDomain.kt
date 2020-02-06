package com.cristhianbonilla.com.chilapp.domain.contrats.dashboard

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.SecretPostRvAdapter

interface ListenerDomain{

    fun onReadSecretPost(
        secretpostList: ArrayList<SecretPost>,
        root: RecyclerView?,
        secretPostRvAdapter: SecretPostRvAdapter
    )
    fun saveSecretPost(contex: Context, message: String, user: UserDto)
    fun getSecretsPost(
        user: UserDto?,
        root: RecyclerView?,
        secretPostRvAdapter: SecretPostRvAdapter
    )
}