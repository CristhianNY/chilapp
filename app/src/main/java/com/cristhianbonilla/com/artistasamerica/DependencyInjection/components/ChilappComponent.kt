package com.cristhianbonilla.com.artistasamerica.DependencyInjection.components

import com.cristhianbonilla.com.artistasamerica.DependencyInjection.module.ChilappModule
import com.cristhianbonilla.com.artistasamerica.DependencyInjection.module.ViewModelModule
import com.cristhianbonilla.com.artistasamerica.domain.comments.CommentsDomain
import com.cristhianbonilla.com.artistasamerica.domain.comments.repository.CommentsRepository
import com.cristhianbonilla.com.artistasamerica.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.artistasamerica.domain.dashboard.repository.DashBoardRepository
import com.cristhianbonilla.com.artistasamerica.domain.home.HomeDomain
import com.cristhianbonilla.com.artistasamerica.domain.home.repository.HomeRepository
import com.cristhianbonilla.com.artistasamerica.domain.meetings.MeetingDomain
import com.cristhianbonilla.com.artistasamerica.ui.activities.MainActivity
import com.cristhianbonilla.com.artistasamerica.ui.activities.Splash.SplashActivity
import com.cristhianbonilla.com.artistasamerica.ui.activities.login.LoginActivty
import com.cristhianbonilla.com.artistasamerica.ui.activities.meeting.ZoomMeetingActivity
import com.cristhianbonilla.com.artistasamerica.ui.activities.register.RegisterActivity
import com.cristhianbonilla.com.artistasamerica.ui.fragments.addSecret.AddSecretDialogFragment
import com.cristhianbonilla.com.artistasamerica.ui.fragments.comments.CommentsDialogFragment
import com.cristhianbonilla.com.artistasamerica.ui.fragments.dashboard.DashboardFragment
import com.cristhianbonilla.com.artistasamerica.ui.fragments.home.HomeFragment
import com.cristhianbonilla.com.artistasamerica.ui.fragments.profile.ProfileFragment
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AndroidInjectionModule::class, ChilappModule::class, ViewModelModule::class])
interface ChilappComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(loginActivty: LoginActivty)
    fun inject(registerActivity: RegisterActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(dashboardFragment: DashboardFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(zoomMeetingActivity: ZoomMeetingActivity)
    fun inject(commentsDialogFragment: CommentsDialogFragment)
    fun inject(dashBoardDomain: DashBoardDomain)
    fun inject(homeDomain: HomeDomain)
    fun inject(homeRepository: HomeRepository)
    fun inject(commentsDomain: CommentsDomain)
    fun inject(meetingDomain: MeetingDomain)
    fun inject(dashBoardRepository: DashBoardRepository)
    fun inject(commentsRepository: CommentsRepository)
    fun inject(addSecretDialogFragment: AddSecretDialogFragment)
}