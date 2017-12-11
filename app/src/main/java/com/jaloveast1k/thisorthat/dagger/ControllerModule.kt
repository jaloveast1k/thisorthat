package com.jaloveast1k.thisorthat.dagger

import com.jaloveast1k.thisorthat.view.BaseFragment
import com.jaloveast1k.thisorthat.viewmodel.MainViewModel
import com.jaloveast1k.thisorthat.viewmodel.SplashViewModel
import dagger.Module
import dagger.Provides
import org.olu.mvvm.viewmodel.QuestionsRepository
import org.olu.mvvm.viewmodel.RegistrationRepository

@Module
class ControllerModule(private val fragment: BaseFragment) {
    @Provides
    @ControllerScope
    internal fun provideFragment(): BaseFragment {
        return fragment
    }

    @Provides
    @ControllerScope
    fun provideSplashViewModel(registrationRepository: RegistrationRepository) = SplashViewModel(registrationRepository)

    @Provides
    @ControllerScope
    fun provideMainViewModel(questionsRepository: QuestionsRepository) = MainViewModel(questionsRepository)
}