package com.jaloveast1k.thisorthat.viewmodel

import com.jaloveast1k.thisorthat.repository.data.client
import com.jaloveast1k.thisorthat.repository.data.pojo.ResponseRegistration
import io.reactivex.Observable
import org.olu.mvvm.viewmodel.RegistrationRepository
import java.util.*

class SplashViewModel(val registrationRepository: RegistrationRepository) {
    fun registration(): Observable<ResponseRegistration> {
        return registrationRepository.registration(client, UUID.randomUUID().toString())
    }
}
