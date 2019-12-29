package com.cristhianbonilla.com.chilapp

import android.app.Application
import com.cristhianbonilla.com.chilapp.DependencyInjection.module.ChilappComponent
import com.cristhianbonilla.com.chilapp.DependencyInjection.module.ChilappModule
import com.cristhianbonilla.com.chilapp.DependencyInjection.module.DaggerChilappComponent

private lateinit var chilappComponent: ChilappComponent
class App : Application(){

    override fun onCreate() {
        super.onCreate()

        chilappComponent = DaggerChilappComponent.builder().chilappModule(ChilappModule()).build()
    }

    fun getComponent() = chilappComponent

}