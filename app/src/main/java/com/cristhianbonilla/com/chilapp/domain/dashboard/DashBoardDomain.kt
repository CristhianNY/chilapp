package com.cristhianbonilla.com.chilapp.domain.dashboard

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.domain.base.Result
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerActivity
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.domain.dashboard.repository.DashBoardRepository
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.ui.activities.MainActivity
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.SecretPostRvAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class DashBoardDomain @Inject constructor( listenerActivity:ListenerActivity ) : ViewModel(), ListenerDomain{

    var listenerActiv: ListenerActivity
    var dashBoardRepository:DashBoardRepository
    var postList: MutableLiveData<List<SecretPost>> = MutableLiveData()

    init {
        App.instance.getComponent().inject(this)

        listenerActiv = listenerActivity
        dashBoardRepository = DashBoardRepository(this)
    }

    override fun saveSecretPost(contex : Context, message: String, user: UserDto) {

       dashBoardRepository.saveSecretPost(contex,  message, user)
    }

    suspend fun saveSecretPostToFirebase(contex : Context, message: String, user: UserDto){
        dashBoardRepository.saveSecretPostToFirebaseStore(contex,  message, user)
    }

        suspend fun makeLike(secretPost: SecretPost,contex: Context,user: UserDto){

        var sumLikes =secretPost.likes +1
        dashBoardRepository.likeSecretPost(secretPost,contex,user , sumLikes)

    }

    fun makeDislike(secretPost: SecretPost,contex: Context,user: UserDto){
        if(secretPost.likes>0){
            var sumLikes =secretPost.likes -1
            dashBoardRepository.makeDislikeSecretPost(secretPost,contex,user,sumLikes)
        }
    }
    suspend fun getSecretPostLiked(secretPost: SecretPost,user: UserDto):Boolean {

        val postLiked = dashBoardRepository.getPostLikedByMe(secretPost,user)
        var lisOfPostLiked :List<SecretPost> = ArrayList()

        when (postLiked) {
            is Result.Value -> lisOfPostLiked = postLiked.value
        }

        for(i in lisOfPostLiked){
            if(i.id.equals(secretPost.id)){
                return true
            }
        }
       return  return false
    }

    suspend fun getSecretPostFromFirebaseRealTIme(user: UserDto){

        val secretpostlist = ArrayList<SecretPost>()
        dashBoardRepository.getSecretPostRealTimeDataBase(user).addValueEventListener(object :
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
    suspend fun getSecretPostLikesFromFirestore(user: UserDto) : List<SecretPost>{

        var secretPost :List<SecretPost> = ArrayList()
        var  secretPostResult =   dashBoardRepository.getSecretPost(user)

        when(secretPostResult){
            is Result.Value -> secretPost = secretPostResult.value
        }

        return secretPost
    }

}
