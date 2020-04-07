package com.cristhianbonilla.com.artistasamerica

import android.app.Application
import com.cristhianbonilla.com.artistasamerica.DependencyInjection.components.ChilappComponent
import com.cristhianbonilla.com.artistasamerica.DependencyInjection.components.DaggerChilappComponent
import com.cristhianbonilla.com.artistasamerica.DependencyInjection.module.ChilappModule


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