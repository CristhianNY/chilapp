package com.cristhianbonilla.com.chilapp.domain.dashboard

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerActivity
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.domain.dashboard.repository.DashBoardRepository
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class DashBoardDomain @Inject constructor( listenerActivity:ListenerActivity ) : ViewModel(), ListenerDomain{

    var listenerActiv: ListenerActivity
    var dashBoardRepository:DashBoardRepository
    var postList: MutableLiveData<List<SecretPost>> = MutableLiveData()
    var postLiked: MutableLiveData<List<SecretPost>> = MutableLiveData()

    init {
        App.instance.getComponent().inject(this)

        listenerActiv = listenerActivity
        dashBoardRepository = DashBoardRepository(this)
    }

    override fun saveSecretPost(
        contex: Context,
        message: String,
        user: UserDto,
        colorPost: String
    ) {

       dashBoardRepository.saveSecretPost(contex,  message, user,colorPost)
    }


     fun makeLike(secretPost: SecretPost,contex: Context,user: UserDto){

        var sumLikes =secretPost.likes +1
        dashBoardRepository.likeSecretPost(secretPost,contex,user , sumLikes)

    }

    fun makeDislike(secretPost: SecretPost,contex: Context,user: UserDto){
        if(secretPost.likes>0){
            var sumLikes =secretPost.likes -1
            dashBoardRepository.makeDislikeSecretPost(secretPost,contex,user,sumLikes)
        }
    }

    fun saveAnimationPreference(isAnimationShowed: Boolean, contex: Context){
        dashBoardRepository.saveAnimationPreference(contex,isAnimationShowed)
    }

    fun getAnimationPreference(contex: Context) :Boolean{
        return dashBoardRepository.getAnimationPreference(contex)
    }

   fun deleteAnimationPreference(contex: Context){
        return dashBoardRepository.deleteAnimationPreference(contex)
    }

    suspend fun getSecretPostFromFirebaseRealTIme(user: UserDto){

        val secretpostlist = ArrayList<SecretPost>()
        dashBoardRepository.getSecretPostRealTimeDataBase(user).limitToLast(100).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                secretpostlist.clear()
                for (postSnapshot in dataSnapshot.children) {

                    var sercretPost = postSnapshot.getValue(SecretPost::class.java)
                    sercretPost?.let { secretpostlist.add(it) }

                    postList.value  = secretpostlist
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("onCancelled", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    suspend fun getSecretPostLikes(user: UserDto){

        val secretpostlist = ArrayList<SecretPost>()
        dashBoardRepository.getSecretPostRealTimeDataBaseLikes(user).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                secretpostlist.clear()
                for (postSnapshot in dataSnapshot.children) {
                    var sercretPost = postSnapshot.getValue(SecretPost::class.java)
                    sercretPost?.let { secretpostlist.add(it) }

                    postLiked.value  = secretpostlist
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("onCancelled", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
}
