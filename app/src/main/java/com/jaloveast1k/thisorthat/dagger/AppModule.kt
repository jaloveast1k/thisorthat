package com.jaloveast1k.thisorthat.dagger

import android.arch.persistence.room.Room
import com.jaloveast1k.thisorthat.App
import com.jaloveast1k.thisorthat.repository.api.Api
import com.jaloveast1k.thisorthat.repository.data.PreferencesHelper
import com.jaloveast1k.thisorthat.repository.db.AppDatabase
import dagger.Module
import dagger.Provides
import org.olu.mvvm.viewmodel.RegistrationRepository
import javax.inject.Singleton

@Module
class AppModule(val app: App) {
    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton
    fun provideApi() = Api.create()

    @Provides
    @Singleton
    fun providePreferencesHelper() = PreferencesHelper(app)

    @Provides
    @Singleton
    fun provideRegistrationRepository(api: Api, preferencesHelper: PreferencesHelper) = RegistrationRepository(api, preferencesHelper)

    @Provides
    @Singleton
    fun provideDatabase(app: App) = Room.databaseBuilder(app, AppDatabase::class.java, "this-or-that-database").build()
}