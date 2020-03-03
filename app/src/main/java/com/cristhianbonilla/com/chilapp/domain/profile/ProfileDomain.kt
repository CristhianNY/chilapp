package com.cristhianbonilla.com.chilapp.domain.profile

import android.content.Context
import android.view.View
import com.cristhianbonilla.com.chilapp.domain.contrats.profile.ProfileFragmentListerner
import com.cristhianbonilla.com.chilapp.domain.contrats.profile.ProfileListenerDomain
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

    fun getUserInformation(context: Context,userDto: UserDto?, root: View){
        profileRepository.getUserInformation(context,userDto,root)
    }

    override fun userInformatinRead(userDto: UserDto? , root:View) {
        listenerfragment.onUserInformatinRead(userDto,root)
    }
}