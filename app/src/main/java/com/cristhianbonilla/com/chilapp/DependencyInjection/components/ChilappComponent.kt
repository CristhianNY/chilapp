package com.cristhianbonilla.com.chilapp.DependencyInjection.components

import com.cristhianbonilla.com.chilapp.DependencyInjection.module.ChilappModule
import com.cristhianbonilla.com.chilapp.MainActivity
import com.cristhianbonilla.com.chilapp.ui.activities.Splash.SplashActivity
import com.cristhianbonilla.com.chilapp.ui.activities.login.LoginActivty
import com.cristhianbonilla.com.chilapp.ui.activities.register.RegisterActivity
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.DashboardFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ChilappModule::class])
interface ChilappComponent{

    fun inject(mainActivity: MainActivity)
    fun inject(loginActivty: LoginActivty)
    fun inject(registerActivity: RegisterActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(dashboardFragment: DashboardFragment)
}