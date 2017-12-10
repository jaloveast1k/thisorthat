package com.jaloveast1k.thisorthat.dagger

import com.jaloveast1k.thisorthat.App
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: App)

    fun plus(controllerModule: ControllerModule): ControllerComponent
}