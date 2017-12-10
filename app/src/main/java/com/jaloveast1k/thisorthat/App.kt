package com.jaloveast1k.thisorthat

import android.app.Application
import android.arch.persistence.room.Room
import com.jaloveast1k.thisorthat.dagger.AppComponent
import com.jaloveast1k.thisorthat.dagger.AppModule
import com.jaloveast1k.thisorthat.dagger.DaggerAppComponent
import com.jaloveast1k.thisorthat.repository.api.Api
import com.jaloveast1k.thisorthat.repository.data.PreferencesHelper
import com.jaloveast1k.thisorthat.repository.db.AppDatabase
import com.jaloveast1k.thisorthat.viewmodel.MainViewModel
import com.jaloveast1k.thisorthat.viewmodel.SplashViewModel
import org.olu.mvvm.viewmodel.QuestionsRepository
import org.olu.mvvm.viewmodel.RegistrationRepository
import timber.log.Timber


class App : Application() {
    // TODO: refactor to use dagger
    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        component.inject(this)

        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())
    }
}
