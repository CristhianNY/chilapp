package com.cristhianbonilla.com.chilapp.DependencyInjection.module

import com.cristhianbonilla.com.chilapp.domain.comments.CommentsDomain
import com.cristhianbonilla.com.chilapp.domain.comments.repository.CommentsRepository
import com.cristhianbonilla.com.chilapp.domain.contrats.comments.ListenerCommentsActivity
import com.cristhianbonilla.com.chilapp.domain.contrats.comments.ListenerCommentsDomain
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerActivity
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerDomain
import com.cristhianbonilla.com.chilapp.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.chilapp.domain.dashboard.repository.DashBoardRepository
import com.cristhianbonilla.com.chilapp.domain.dashboard.repository.DashBoardRepositoryInterface
import com.cristhianbonilla.com.chilapp.domain.home.HomeDomain
import com.cristhianbonilla.com.chilapp.domain.home.repository.HomeRepository
import com.cristhianbonilla.com.chilapp.domain.login.LoginDomain
import com.cristhianbonilla.com.chilapp.domain.login.repository.LoginRepository
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.DashboardFragment
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
    fun provideHomeDomain()=
        HomeDomain(
            homerepository = HomeRepository()
        )

    @Provides
    @Singleton
    fun getDashBoardDomain(listenerActivity: ListenerActivity): DashBoardDomain {
        return DashBoardDomain(listenerActivity)
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
}