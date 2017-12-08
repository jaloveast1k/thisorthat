package org.olu.mvvm.viewmodel

import android.content.Context
import com.jaloveast1k.thisorthat.repository.api.Api
import com.jaloveast1k.thisorthat.repository.data.Consts
import com.jaloveast1k.thisorthat.repository.data.PreferenceHelper
import com.jaloveast1k.thisorthat.repository.data.pojo.RequestRegistration
import com.jaloveast1k.thisorthat.repository.data.pojo.ResponseRegistration
import io.reactivex.Observable
import timber.log.Timber
import com.jaloveast1k.thisorthat.repository.data.PreferenceHelper.set
import com.jaloveast1k.thisorthat.repository.data.PreferenceHelper.get

class RegistrationRepository(private val api: Api, private val context: Context) {
    fun registration(client: String, unique: String): Observable<ResponseRegistration> {
        val prefs = PreferenceHelper.defaultPrefs(context)
        val token: String? = prefs[Consts.SharedPrefs.TOKEN]
        val userId: Long? = prefs[Consts.SharedPrefs.USER_ID]
        if (token != null && userId != null) {
            val cachedUser = ResponseRegistration(userId, token)
            Timber.d("User $cachedUser is already logged in")
            return Observable.just(cachedUser)
        }

        return api.registration(RequestRegistration(client, unique))
                .map {
                    prefs[Consts.SharedPrefs.USER_ID] = it.user
                    prefs[Consts.SharedPrefs.TOKEN] = it.token
                    it
                }.doOnNext {
                    Timber.d("Dispatching $it from API...")
                }
    }
}
