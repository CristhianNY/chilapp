package com.cristhianbonilla.com.artistasamerica.DependencyInjection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cristhianbonilla.com.artistasamerica.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.artistasamerica.domain.login.LoginDomain
import com.cristhianbonilla.com.artistasamerica.domain.meetings.MeetingDomain
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DashBoardDomain::class)
    internal abstract fun SearchViewModel(viewModel: DashBoardDomain): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginDomain::class)
    internal abstract fun LoginDomainViewModel(viewModel: LoginDomain): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MeetingDomain::class)
    internal abstract fun MeetingViewModel(viewModel: MeetingDomain): ViewModel

}


