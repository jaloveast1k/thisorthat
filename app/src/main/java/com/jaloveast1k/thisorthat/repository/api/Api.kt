package com.jaloveast1k.thisorthat.repository.api

import com.jaloveast1k.thisorthat.App
import com.jaloveast1k.thisorthat.BuildConfig
import com.jaloveast1k.thisorthat.repository.data.pojo.RequestAddQuestion
import com.jaloveast1k.thisorthat.repository.data.pojo.RequestPostAnswers
import com.jaloveast1k.thisorthat.repository.data.pojo.RequestRegistration
import com.jaloveast1k.thisorthat.repository.data.pojo.ResponseRegistration
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

interface Api {
    @POST("items/add")
    fun addQuestion(@Body request: RequestAddQuestion): Observable<String>

    @GET("items/get/{count}")
    fun getQuestions(@Path("count") count: Int): Observable<String>

    @GET("items/show")
    fun getUserQuestions(): Observable<String>

    @POST("views/add")
    fun postAnswers(@Body request: RequestPostAnswers): Observable<String>

    @POST("users/add")
    fun registration(@Body request: RequestRegistration): Observable<ResponseRegistration>

    companion object Factory {
        fun create(): Api {
            val loggingInterceptor = HttpLoggingInterceptor { Timber.d(it) }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                builder.addNetworkInterceptor(loggingInterceptor)
            }
            val client = builder
                    .addInterceptor({ chain ->
                        val builder = chain.request().newBuilder()

//                    builder.addHeader("android_app_version", String.valueOf(BuildConfig.VERSION_CODE))

                        chain.proceed(builder.build())
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://thisorthat.ru/api/")
                    .client(client)
                    .build()

            return retrofit.create(Api::class.java)
        }
    }
}
