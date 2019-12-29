package com.cristhianbonilla.com.chilapp.DependencyInjection.module

import com.cristhianbonilla.com.domain.LoginDomain
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ChilappModule{

    @Provides
    @Singleton
    fun provideLoginDomain()= LoginDomain()
}