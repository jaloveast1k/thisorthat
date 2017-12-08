package com.jaloveast1k.thisorthat.viewmodel.data

import com.jaloveast1k.thisorthat.repository.data.Question

data class QuestionsList(val users: List<Question>, val message: String, val error: Throwable? = null)
data class UIDataRegistration(val token: String, val message: String, val error: Throwable? = null)