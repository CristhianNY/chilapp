package com.cristhianbonilla.com.chilapp.domain.profile

import android.content.Context
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerActivity
import com.cristhianbonilla.com.chilapp.domain.contrats.profile.ProfileFragmentListerner
import com.cristhianbonilla.com.chilapp.domain.contrats.profile.ProfileListenerDomain
import com.cristhianbonilla.com.chilapp.domain.dashboard.repository.DashBoardRepository
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.domain.profile.repository.ProfileRepository
import javax.inject.Inject

class ProfileDomain @Inject constructor(profileListenerFragment: ProfileFragmentListerner) : ProfileListenerDomain {

    var listenerfragment: ProfileFragmentListerner

    var profileRepository: ProfileRepository

    init {
       // App.instance.getComponent().inject(this)

        listenerfragment = profileListenerFragment
        profileRepository = ProfileRepository(this)
    }

    fun getUserInformation(context: Context,userDto: UserDto?){
        profileRepository.getUserInformation(context,userDto)
    }


    override fun userInformatinRead(userDto: UserDto?) {
        listenerfragment.onUserInformatinRead(userDto)
    }
}