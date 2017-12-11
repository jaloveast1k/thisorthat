package com.jaloveast1k.thisorthat.repository.api

import android.util.Base64
import com.jaloveast1k.thisorthat.BuildConfig
import com.jaloveast1k.thisorthat.repository.data.Consts.SharedPrefs.TOKEN
import com.jaloveast1k.thisorthat.repository.data.Consts.SharedPrefs.USER_ID
import com.jaloveast1k.thisorthat.repository.data.PreferencesHelper
import com.jaloveast1k.thisorthat.repository.data.Question
import com.jaloveast1k.thisorthat.repository.data.pojo.*
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import timber.log.Timber
import java.util.concurrent.TimeUnit

interface Api {
    @POST("items/add")
    fun addQuestion(@Body request: RequestAddQuestion): Single<String>

    @GET("items/get/{count}")
    fun getQuestions(@Path("count") count: Int): Single<HashMap<Int, Question>>

    @GET("items/show")
    fun getUserQuestions(): Single<String>

    @POST("views/add")
    fun postAnswers(@Body request: RequestPostAnswers): Completable

    @POST("users/add")
    fun postRegistration(@Body request: RequestRegistration): Single<ResponseRegistration>

    companion object Factory {
        fun create(preferencesHelper: PreferencesHelper): Api {
            val loggingInterceptor = HttpLoggingInterceptor { Timber.d(it) }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                builder.addNetworkInterceptor(loggingInterceptor)
            }
            val client = builder
                    .addInterceptor({ chain ->
                        val builder = chain.request().newBuilder()

                        val token: String? = preferencesHelper.getValue(TOKEN)
                        val id: Long? = preferencesHelper.getValue(USER_ID)

                        if (token != null && id != null) {
                            val auth = "$id:$token"
                            var auth64 = Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP or Base64.URL_SAFE)
                            builder.addHeader("Authorization", "Basic $auth64")
                        }

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
