package com.cristhianbonilla.com.chilapp.domain.dashboard

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerActivity
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.domain.dashboard.repository.DashBoardRepository
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
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
        root: RecyclerView?
    ){

        dashBoardRepository.readSecrePost(user, root)

    }

    override fun onReadSecretPost(secretpostList: ArrayList<SecretPost>, root: RecyclerView?) {

          listenerActiv.onSecretPostRead(secretpostList , root)
    }
}
