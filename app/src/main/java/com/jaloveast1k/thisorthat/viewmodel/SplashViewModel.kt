package com.jaloveast1k.thisorthat.viewmodel

import com.jaloveast1k.thisorthat.repository.data.client
import com.jaloveast1k.thisorthat.repository.data.pojo.ResponseRegistration
import io.reactivex.Observable
import org.olu.mvvm.viewmodel.RegistrationRepository
import java.util.*

class SplashViewModel(val registrationRepository: RegistrationRepository) {
    fun registration(): Observable<ResponseRegistration> {
        return registrationRepository.registration(client, UUID.randomUUID().toString())
//                .debounce(400, TimeUnit.MILLISECONDS)
//                .map {
//                    Timber.d("Mapping users to UIData...")
//                    Registration(it.token, "Registration token")
//                }
//                .onErrorReturn {
//                    Registration("", "An error occurred", it)
//                }
    }
}
