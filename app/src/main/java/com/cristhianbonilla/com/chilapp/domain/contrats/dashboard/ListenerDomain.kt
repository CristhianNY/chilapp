package com.cristhianbonilla.com.chilapp.domain.contrats.dashboard

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.activities.MainActivity
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.SecretPostRvAdapter

interface ListenerDomain{
    fun saveSecretPost(contex: Context, message: String, user: UserDto)
}