package com.jaloveast1k.thisorthat

import android.app.Application
import android.arch.persistence.room.Room
import android.util.Log
import com.jaloveast1k.thisorthat.repository.api.Api
import com.jaloveast1k.thisorthat.repository.db.AppDatabase
import com.jaloveast1k.thisorthat.viewmodel.MainViewModel
import com.jaloveast1k.thisorthat.viewmodel.SplashViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.olu.mvvm.viewmodel.QuestionsRepository
import org.olu.mvvm.viewmodel.RegistrationRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit


class App : Application() {
    // TODO: refactor to use dagger
    companion object {
        private lateinit var questionsRepository: QuestionsRepository
        private lateinit var registrationRepository: RegistrationRepository

        private lateinit var api: Api
        private lateinit var mainViewModel: MainViewModel
        private lateinit var splashViewModel: SplashViewModel
        private lateinit var appDatabase: AppDatabase

        fun injectUserApi() = api

        fun injectUserListViewModel() = mainViewModel
        fun injectSplashViewModel() = splashViewModel

        fun injectUserDao() = appDatabase.questionDao()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())

        appDatabase = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "mvvm-database").build()

        api = Api.create()

        questionsRepository = QuestionsRepository(api, appDatabase.questionDao())
        registrationRepository = RegistrationRepository(api, this)

        mainViewModel = MainViewModel(questionsRepository)
        splashViewModel = SplashViewModel(registrationRepository)
    }
}
