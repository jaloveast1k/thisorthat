package com.jaloveast1k.thisorthat.dagger

import com.jaloveast1k.thisorthat.view.MVVMFragment
import com.jaloveast1k.thisorthat.viewmodel.SplashViewModel
import dagger.Module
import dagger.Provides
import org.olu.mvvm.viewmodel.RegistrationRepository

@Module
class ControllerModule(private val fragment: MVVMFragment) {
    @Provides
    @ControllerScope
    internal fun provideFragment(): MVVMFragment {
        return fragment
    }

    @Provides
    @ControllerScope
    fun provideSplashViewModel(registrationRepository: RegistrationRepository) = SplashViewModel(registrationRepository)
}