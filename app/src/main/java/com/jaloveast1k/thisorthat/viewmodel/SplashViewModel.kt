package com.jaloveast1k.thisorthat.viewmodel

import com.jaloveast1k.thisorthat.repository.data.Consts.General.CLIENT
import com.jaloveast1k.thisorthat.repository.data.pojo.ResponseRegistration
import io.reactivex.Single
import org.olu.mvvm.viewmodel.RegistrationRepository
import java.util.*

class SplashViewModel(private val registrationRepository: RegistrationRepository) {
    fun registration(): Single<ResponseRegistration> {
        return registrationRepository.registration(CLIENT, UUID.randomUUID().toString())
    }
}
