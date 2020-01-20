package com.cristhianbonilla.com.chilapp.DependencyInjection.module

import com.cristhianbonilla.com.domain.repositories.login.repositories.features.dashboard.DashBoardDomain
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.dashboard.repository.DashBoardRepository
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.home.HomeDomain
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.home.repository.HomeRepository
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.login.LoginDomain
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.login.repository.LoginRepository
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