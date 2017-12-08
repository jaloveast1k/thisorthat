package org.olu.mvvm.viewmodel

import com.jaloveast1k.thisorthat.repository.api.Api
import com.jaloveast1k.thisorthat.repository.db.QuestionDao

class QuestionsRepository(val api: Api, val questionDao: QuestionDao) {
//    fun getUsers(): Observable<List<User>> {
//        return Observable.concatArray(
//                getUsersFromDb(),
//                getUsersFromApi())
//    }
//
//    fun getUsersFromDb(): Observable<List<User>> {
//        return userDao.getUsers().filter { it.isNotEmpty() }
//                .toObservable()
//                .doOnNext {
//                    Timber.d("Dispatching ${it.size} users from DB...")
//                }
//    }
//
//    fun getUsersFromApi(): Observable<List<User>> {
//        return api.getUsers()
//                .doOnNext {
//                    Timber.d("Dispatching ${it.size} users from API...")
//                    storeUsersInDb(it)
//                }
//    }
//
//    fun storeUsersInDb(users: List<User>) {
//        Observable.fromCallable { userDao.insertAll(users) }
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe {
//                    Timber.d("Inserted ${users.size} users from API in DB...")
//                }
//    }
}
