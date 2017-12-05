package com.jaloveast1k.thisorthat.viewmodel.data

import com.jaloveast1k.thisorthat.repository.data.User

data class UsersList(val users: List<User>, val message: String, val error: Throwable? = null)
