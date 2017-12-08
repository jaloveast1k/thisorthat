package com.jaloveast1k.thisorthat

import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import com.jaloveast1k.thisorthat.repository.api.Api
import com.jaloveast1k.thisorthat.repository.data.User
import com.jaloveast1k.thisorthat.repository.db.QuestionDao
import org.olu.mvvm.viewmodel.QuestionsRepository


class QuestionsRepositoryTest {

    lateinit var questionsRepository: QuestionsRepository
    lateinit var api: Api
    lateinit var userDao: QuestionDao

    @Before
    fun setup() {
        api = mock()
        userDao = mock()
        `when`(userDao.getUsers()).thenReturn(Single.just(emptyList()))
        questionsRepository = QuestionsRepository(api, userDao)
    }

    @Test
    fun test_emptyCache_noDataOnApi_returnsEmptyList() {
        `when`(api.getUsers()).thenReturn(Observable.just(emptyList<User>()))

        questionsRepository.getUsers().test()
                .assertValue { it.isEmpty() }
    }

    @Test
    fun test_emptyCache_hasDataOnApi_returnsApiData() {
        `when`(api.getUsers()).thenReturn(Observable.just(listOf(aRandomQuestion())))

        questionsRepository.getUsers().test()
                .assertValueCount(1)
                .assertValue { it.size == 1 }
    }

    @Test
    fun test_hasCacheData_hasApiData_returnsBothData() {
        val cachedData = listOf(aRandomQuestion())
        val apiData = listOf(aRandomQuestion(), aRandomQuestion())
        `when`(api.getUsers()).thenReturn(Observable.just(apiData))
//        questionsRepository.cachedUsers = cachedData

        questionsRepository.getUsers().test()
                //Both cached & API data delivered
                .assertValueCount(2)
                //First cache data delivered
                .assertValueAt(0, { it == cachedData })
                //Secondly api data delivered
                .assertValueAt(1, { it == apiData })
    }

    @Test
    fun test_cache_updatedWithApiData() {
        val apiData = listOf(aRandomQuestion(), aRandomQuestion())
        `when`(api.getUsers()).thenReturn(Observable.just(apiData))

        questionsRepository.getUsers().test()

//        assertEquals(questionsRepository.cachedUsers, apiData)
    }

    private fun aRandomQuestion() = User("mail@test.com", "John", 1, 1, 1, 1, 1, 1, 1)
}