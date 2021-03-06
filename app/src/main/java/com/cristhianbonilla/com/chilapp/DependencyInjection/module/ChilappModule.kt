package com.cristhianbonilla.com.chilapp.DependencyInjection.module

import com.cristhianbonilla.com.chilapp.domain.comments.CommentsDomain
import com.cristhianbonilla.com.chilapp.domain.comments.repository.CommentsRepository
import com.cristhianbonilla.com.chilapp.domain.contrats.comments.ListenerCommentsActivity
import com.cristhianbonilla.com.chilapp.domain.contrats.comments.ListenerCommentsDomain
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerActivity
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerDomain
import com.cristhianbonilla.com.chilapp.domain.contrats.home.HomeListenerDomain
import com.cristhianbonilla.com.chilapp.domain.contrats.home.ListenerHomeFragment
import com.cristhianbonilla.com.chilapp.domain.contrats.profile.ProfileFragmentListerner
import com.cristhianbonilla.com.chilapp.domain.contrats.profile.ProfileListenerDomain
import com.cristhianbonilla.com.chilapp.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.chilapp.domain.dashboard.repository.DashBoardRepository
import com.cristhianbonilla.com.chilapp.domain.dashboard.repository.DashBoardRepositoryInterface
import com.cristhianbonilla.com.chilapp.domain.home.HomeDomain
import com.cristhianbonilla.com.chilapp.domain.home.repository.HomeRepository
import com.cristhianbonilla.com.chilapp.domain.login.LoginDomain
import com.cristhianbonilla.com.chilapp.domain.login.repository.LoginRepository
import com.cristhianbonilla.com.chilapp.domain.profile.ProfileDomain
import com.cristhianbonilla.com.chilapp.domain.profile.repository.ProfileRepository
import com.cristhianbonilla.com.chilapp.ui.fragments.comments.CommentsDialogFragment
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.DashboardFragment
import com.cristhianbonilla.com.chilapp.ui.fragments.home.HomeFragment
import com.cristhianbonilla.com.chilapp.ui.fragments.profile.ProfileFragment
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