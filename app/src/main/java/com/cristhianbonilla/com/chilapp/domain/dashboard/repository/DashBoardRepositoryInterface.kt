package com.cristhianbonilla.com.chilapp.domain.dashboard.repository

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto

interface DashBoardRepositoryInterface{
    fun saveSecretPost(context: Context, message: String , userDto: UserDto?)
    fun readSecrePost( userDto: UserDto?, root: RecyclerView?)
}