package com.cristhianbonilla.com.chilapp.DependencyInjection.components

import com.cristhianbonilla.com.chilapp.DependencyInjection.module.ChilappModule
import com.cristhianbonilla.com.chilapp.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.chilapp.domain.dashboard.repository.DashBoardRepository
import com.cristhianbonilla.com.chilapp.ui.activities.MainActivity
import com.cristhianbonilla.com.chilapp.ui.activities.Splash.SplashActivity
import com.cristhianbonilla.com.chilapp.ui.activities.login.LoginActivty
import com.cristhianbonilla.com.chilapp.ui.activities.register.RegisterActivity
import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.DashboardFragment
import com.cristhianbonilla.com.chilapp.ui.fragments.home.HomeFragment
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,AndroidInjectionModule::class,ChilappModule::class])
interface ChilappComponent{

    fun inject(mainActivity: MainActivity)
    fun inject(loginActivty: LoginActivty)
    fun inject(registerActivity: RegisterActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(dashboardFragment: DashboardFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(dashBoardDomain: DashBoardDomain)
    fun inject(dashBoardRepository: DashBoardRepository)
}