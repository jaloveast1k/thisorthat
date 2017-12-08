package com.jaloveast1k.thisorthat.viewmodel

import org.olu.mvvm.viewmodel.QuestionsRepository

class MainViewModel(val questionsRepository: QuestionsRepository) {
//    fun getUsers(): Observable<UsersList> {
//        //Create the data for your UI, the users lists and maybe some additional data needed as well
//        return questionsRepository.getUsers()
//                //Drop DB data if we can fetch item fast enough from the API
//                //to avoid UI flickers
//                .debounce(400, TimeUnit.MILLISECONDS)
//                .map {
//                    Timber.d("Mapping users to UIData...")
//                    UsersList(it.take(10), "Top 10 Users")
//                }
//                .onErrorReturn {
//                    UsersList(emptyList(), "An error occurred", it)
//                }
//    }
}
