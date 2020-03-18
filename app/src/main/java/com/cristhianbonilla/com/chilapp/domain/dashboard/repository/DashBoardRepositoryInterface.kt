package com.cristhianbonilla.com.chilapp.domain.dashboard.repository

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.domain.base.Result
import com.cristhianbonilla.com.chilapp.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.activities.MainActivity
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.SecretPostRvAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference

interface DashBoardRepositoryInterface{
    fun saveSecretPost(context: Context, message: String , userDto: UserDto?, color:String)
    fun likeSecretPost(secretPost: SecretPost,context: Context,userDto: UserDto?, sumLikes:Int)
    fun makeDislikeSecretPost(secretPost: SecretPost,context: Context,userDto: UserDto?,sumLikes:Int)
    suspend fun getSecretPost(  userDto: UserDto?):Result<Exception,List<SecretPost>>
    suspend fun getSecretPostRealTimeDataBase(userDto: UserDto?) : DatabaseReference
    suspend fun getSecretPostRealTimeDataBaseLikes(userDto: UserDto?) : DatabaseReference
    suspend fun getPostLikedByMe(secretPost: SecretPost,userDto: UserDto?):Result<Exception,List<SecretPost>>

}