package com.cristhianbonilla.com.chilapp.DependencyInjection.module

import com.cristhianbonilla.com.domain.repositories.login.repositories.features.login.LoginDomain
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.login.LoginRepository
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
}