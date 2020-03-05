package com.cristhianbonilla.com.chilapp.domain.dashboard

import android.content.Context
import androidx.fragment.app.FragmentActivity
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
import javax.inject.Inject

class DashBoardDomain @Inject constructor( listenerActivity:ListenerActivity ) : ListenerDomain{

  var listenerActiv: ListenerActivity

    var dashBoardRepository:DashBoardRepository

    init {
        App.instance.getComponent().inject(this)

        listenerActiv = listenerActivity
        dashBoardRepository = DashBoardRepository(this)
    }

    override fun saveSecretPost(contex : Context, message: String, user: UserDto) {

       dashBoardRepository.saveSecretPost(contex,  message, user)
    }

    override fun getSecretsPost(
        user: UserDto?,
        root: RecyclerView?,
        secretPostRvAdapter: SecretPostRvAdapter
    ){

        dashBoardRepository.readSecrePost(user, root , secretPostRvAdapter)

    }

    override fun onReadSecretPost(
        secretpostList: ArrayList<SecretPost>,
        root: RecyclerView?,
        secretPostRvAdapter: SecretPostRvAdapter
    ) {

          listenerActiv.onSecretPostRead(secretpostList , root, secretPostRvAdapter)
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
}
