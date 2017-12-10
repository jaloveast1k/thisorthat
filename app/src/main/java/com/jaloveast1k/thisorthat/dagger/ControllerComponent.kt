package com.jaloveast1k.thisorthat.dagger

import com.jaloveast1k.thisorthat.view.SplashFragment
import dagger.Subcomponent

@ControllerScope
@Subcomponent(modules = [ControllerModule::class])
interface ControllerComponent {
    fun inject(splashFragment: SplashFragment)
}