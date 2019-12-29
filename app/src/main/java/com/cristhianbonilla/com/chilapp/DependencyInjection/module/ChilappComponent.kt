package com.cristhianbonilla.com.chilapp.DependencyInjection.module

import com.cristhianbonilla.com.chilapp.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ChilappModule::class])
interface ChilappComponent{

    fun inject(mainActivity: MainActivity)
}