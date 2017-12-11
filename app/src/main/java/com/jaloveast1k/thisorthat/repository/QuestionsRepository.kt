package org.olu.mvvm.viewmodel

import com.jaloveast1k.thisorthat.repository.api.Api
import com.jaloveast1k.thisorthat.repository.data.Consts
import com.jaloveast1k.thisorthat.repository.data.Question
import com.jaloveast1k.thisorthat.repository.data.pojo.RequestPostAnswers
import com.jaloveast1k.thisorthat.repository.db.QuestionDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class QuestionsRepository(val api: Api, val questionsDao: QuestionDao) {
    fun getQuestions(): Single<List<Question>> {
        return getQuestsFromDb()
                .flatMap {
                    if (it.isNotEmpty())
                        Single.just(it)
                    else
                        getQuestsFromApi()
                }
    }

    fun postAnswers(questions: List<Question>): Completable {
        var answers = ArrayList<HashMap<Int, String>>()
        questions.forEach({
            var map: HashMap<Int, String> = HashMap()
            map.put(it.serverId, it.answer)
            answers.add(map)
        })
        return api.postAnswers(RequestPostAnswers(answers))
                .andThen(deleteQuestsFromDb(questions))
    }

    fun storeQuestionInDb(question: Question): Single<Question> {
        return Single.fromCallable {
            questionsDao.insert(question)
            question
        }
                .doOnSuccess {
                    Timber.d("Inserted $question from API in DB...")
                }
    }

    private fun getQuestsFromDb(): Single<List<Question>> {
        return questionsDao.getQuestions()
                .doOnSuccess {
                    Timber.d("Dispatching ${it.size} questions from DB...")
                }
    }

    private fun deleteQuestsFromDb(questions: List<Question>): Completable {
        return Completable.fromCallable { questionsDao.deleteQuestions(questions) }
                .doOnComplete {
                    Timber.d("Deleted ${questions.size} questions from DB...")
                }
    }

    private fun getQuestsFromApi(): Single<List<Question>> {
        return api.getQuestions(Consts.General.NUMBER_OF_QUESTIONS)
                .map {
                    var iter = it.iterator()
                    iter.forEach {
                        it.value.serverId = it.key
                        it.value.answer = Consts.General.ANSWER_NO
                    }
                    ArrayList(it.values) as List<Question>
                }
                .doOnSuccess {
                    Timber.d("Dispatching ${it.size} questions from API...")
                    storeQuestionsInDb(it)
                }
    }

    private fun storeQuestionsInDb(questions: List<Question>) {
        // TODO: по-моему тут нужно отписываться, либо я чего-то не понимаю
        Observable.fromCallable { questionsDao.insertAll(questions) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Timber.d("Inserted ${questions.size} questions from API in DB...")
                }
    }
}
