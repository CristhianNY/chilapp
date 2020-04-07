package com.cristhianbonilla.com.artistasamerica.domain.dashboard.repository

import android.content.Context
import com.cristhianbonilla.com.artistasamerica.domain.base.Result
import com.cristhianbonilla.com.artistasamerica.domain.dtos.SecretPost
import com.cristhianbonilla.com.artistasamerica.domain.dtos.UserDto
import com.google.firebase.database.DatabaseReference

interface DashBoardRepositoryInterface{
    fun saveSecretPost(context: Context, message: String , userDto: UserDto?, color:String)
    fun likeSecretPost(secretPost: SecretPost,context: Context,userDto: UserDto?, sumLikes:Int)
    fun makeDislikeSecretPost(secretPost: SecretPost,context: Context,userDto: UserDto?,sumLikes:Int)
    suspend fun getSecretPost(  userDto: UserDto?):Result<Exception,List<SecretPost>>
    suspend fun getSecretPostRealTimeDataBase(userDto: UserDto?) : DatabaseReference
    suspend fun getALlSecretPostRealTimeDataBase() : DatabaseReference
    suspend fun getSecretPostRealTimeDataBaseLikes(userDto: UserDto?) : DatabaseReference
    suspend fun getPostLikedByMe(secretPost: SecretPost,userDto: UserDto?):Result<Exception,List<SecretPost>>
    fun saveAnimationPreference(context: Context, isFirstTimeShowingAnimation:Boolean)
    fun getAnimationPreference(context: Context):Boolean
    fun deleteAnimationPreference (context: Context)
}