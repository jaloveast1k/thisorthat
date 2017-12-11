package org.olu.mvvm.viewmodel

import com.jaloveast1k.thisorthat.repository.api.Api
import com.jaloveast1k.thisorthat.repository.data.Consts
import com.jaloveast1k.thisorthat.repository.data.PreferencesHelper
import com.jaloveast1k.thisorthat.repository.data.pojo.RequestRegistration
import com.jaloveast1k.thisorthat.repository.data.pojo.ResponseRegistration
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber

class RegistrationRepository(private val api: Api, private val prefs: PreferencesHelper) {
    fun registration(client: String, unique: String): Single<ResponseRegistration> {
        val token: String? = prefs.getValue(Consts.SharedPrefs.TOKEN)
        val userId: Long? = prefs.getValue(Consts.SharedPrefs.USER_ID)
        if (token != null && userId != null) {
            val cachedUser = ResponseRegistration(userId, token)
            Timber.d("User $cachedUser is already logged in")
            return Single.just(cachedUser)
        }

        return api.postRegistration(RequestRegistration(client, unique))
                .doOnSuccess {
                    prefs.setValue(Consts.SharedPrefs.USER_ID, it.user)
                    prefs.setValue(Consts.SharedPrefs.TOKEN, it.token)

                    Timber.d("Dispatching $it from API...")
                }
    }
}
