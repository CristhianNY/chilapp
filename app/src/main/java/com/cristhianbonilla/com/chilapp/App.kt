package com.cristhianbonilla.com.chilapp

import android.app.Application
import com.cristhianbonilla.com.chilapp.DependencyInjection.components.ChilappComponent
import com.cristhianbonilla.com.chilapp.DependencyInjection.components.DaggerChilappComponent
import com.cristhianbonilla.com.chilapp.DependencyInjection.module.ChilappModule


private lateinit var chilappComponent: ChilappComponent
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        instance = this
        chilappComponent = DaggerChilappComponent.builder().chilappModule(ChilappModule()).build()
    }

    fun getComponent() = chilappComponent

    companion object {
        lateinit var instance : App private set
    }
}