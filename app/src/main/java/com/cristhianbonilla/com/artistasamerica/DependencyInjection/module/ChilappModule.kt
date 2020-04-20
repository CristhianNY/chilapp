package com.cristhianbonilla.com.artistasamerica.DependencyInjection.module

import com.cristhianbonilla.com.artistasamerica.domain.comments.CommentsDomain
import com.cristhianbonilla.com.artistasamerica.domain.comments.repository.CommentsRepository
import com.cristhianbonilla.com.artistasamerica.domain.contrats.comments.ListenerCommentsActivity
import com.cristhianbonilla.com.artistasamerica.domain.contrats.comments.ListenerCommentsDomain
import com.cristhianbonilla.com.artistasamerica.domain.contrats.dashboard.ListenerActivity
import com.cristhianbonilla.com.artistasamerica.domain.contrats.dashboard.ListenerDomain
import com.cristhianbonilla.com.artistasamerica.domain.contrats.home.HomeListenerDomain
import com.cristhianbonilla.com.artistasamerica.domain.contrats.home.ListenerHomeFragment
import com.cristhianbonilla.com.artistasamerica.domain.contrats.profile.ProfileFragmentListerner
import com.cristhianbonilla.com.artistasamerica.domain.contrats.profile.ProfileListenerDomain
import com.cristhianbonilla.com.artistasamerica.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.artistasamerica.domain.dashboard.repository.DashBoardRepository
import com.cristhianbonilla.com.artistasamerica.domain.home.HomeDomain
import com.cristhianbonilla.com.artistasamerica.domain.home.repository.HomeRepository
import com.cristhianbonilla.com.artistasamerica.domain.login.LoginDomain
import com.cristhianbonilla.com.artistasamerica.domain.login.repository.LoginRepository
import com.cristhianbonilla.com.artistasamerica.domain.meetings.MeetingDomain
import com.cristhianbonilla.com.artistasamerica.domain.meetings.repository.MeetingRepository
import com.cristhianbonilla.com.artistasamerica.domain.profile.ProfileDomain
import com.cristhianbonilla.com.artistasamerica.domain.profile.repository.ProfileRepository
import com.cristhianbonilla.com.artistasamerica.ui.fragments.comments.CommentsDialogFragment
import com.cristhianbonilla.com.artistasamerica.ui.fragments.dashboard.DashboardFragment
import com.cristhianbonilla.com.artistasamerica.ui.fragments.home.HomeFragment
import com.cristhianbonilla.com.artistasamerica.ui.fragments.profile.ProfileFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ChilappModule{

    @Provides
    @Singleton
    fun provideLoginDomain()=
        LoginDomain(
            loginRepository = LoginRepository()
        )


    @Provides
    @Singleton
    fun provideMeetingDomain()=
        MeetingDomain()


    @Provides
    @Singleton
    fun getDashBoardDomain(listenerActivity: ListenerActivity): DashBoardDomain {
        return DashBoardDomain(listenerActivity)
    }

    @Provides
    @Singleton
    fun getHomeDomain(listenerActivity: ListenerHomeFragment): HomeDomain {
        return HomeDomain(listenerActivity)
    }


    @Provides
    @Singleton
    fun getDashBoardDomain2(listenerActivity: ListenerActivity): ListenerDomain {
        return DashBoardDomain(listenerActivity)
    }

    @Provides
    @Singleton
    fun getDashBoardRepository(listenerDomain: ListenerDomain): DashBoardRepository {
        return DashBoardRepository(listenerDomain)
    }

    @Provides
    @Singleton
    fun getHomeRepository(listenerDomain: HomeListenerDomain): HomeRepository {
        return HomeRepository(listenerDomain)
    }

    @Provides
    @Singleton
    fun getCommentsDomain(listenerActivity: ListenerCommentsActivity): CommentsDomain {
        return CommentsDomain(listenerActivity)
    }

    @Provides
    @Singleton
    fun getCommentsRepository(listenerDomain: ListenerCommentsDomain): CommentsRepository {
        return CommentsRepository(listenerDomain)
    }

    @Provides
    fun proviedeListenerActivity(): ListenerActivity = DashboardFragment()


    @Provides
    fun provideListenerFriendsFragments(): ListenerHomeFragment = HomeFragment()

    @Provides
    fun proviedeListenerCommentsActivity(): ListenerCommentsActivity = CommentsDialogFragment()


    @Provides
    fun provieProfileListener(): ProfileFragmentListerner = ProfileFragment()

    @Provides
    @Singleton
    fun getProfileRepository(listenerDomain: ProfileListenerDomain): ProfileRepository {
        return getProfileRepository(listenerDomain)
    }

    @Provides
    @Singleton
    fun getProfileDomain(profileFragmentListerner: ProfileFragmentListerner): ProfileDomain {
        return ProfileDomain(profileFragmentListerner)
    }


}