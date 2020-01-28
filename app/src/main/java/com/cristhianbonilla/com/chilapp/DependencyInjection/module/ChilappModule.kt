package com.cristhianbonilla.com.chilapp.DependencyInjection.module

import com.cristhianbonilla.com.chilapp.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.chilapp.domain.dashboard.repository.DashBoardRepository
import com.cristhianbonilla.com.chilapp.domain.home.HomeDomain
import com.cristhianbonilla.com.chilapp.domain.home.repository.HomeRepository
import com.cristhianbonilla.com.chilapp.domain.login.LoginDomain
import com.cristhianbonilla.com.chilapp.domain.login.repository.LoginRepository
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
    fun provideDashBoardDomain()=
        DashBoardDomain(
            dashBoardRepository = DashBoardRepository()
        )
}