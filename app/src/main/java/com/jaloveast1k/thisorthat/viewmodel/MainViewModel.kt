package com.jaloveast1k.thisorthat.viewmodel

import com.jaloveast1k.thisorthat.repository.data.Consts.General.ANSWER_NO
import com.jaloveast1k.thisorthat.repository.data.Consts.General.ANSWER_SKIP
import com.jaloveast1k.thisorthat.repository.data.Question
import io.reactivex.Single
import org.olu.mvvm.viewmodel.QuestionsRepository

class MainViewModel(private val questionsRepository: QuestionsRepository) {
    private var currQuestion: Question? = null
    private var questions: List<Question> = emptyList()

    fun getQuestion(): Single<Question> {
        if (currQuestion != null) {
            return Single.just(currQuestion)
        }

        return nextQuestion()
    }

    // returns next question if current questions is already answered
    // returns current question otherwise
    fun answer(answer: String): Single<Question> {
        if (currQuestion == null)
            return Single.error(IllegalStateException("here is no question to answer"))

        return if (currQuestion?.answer == ANSWER_NO) {
            currQuestion?.answer = answer
            questionsRepository.storeQuestionInDb(currQuestion!!)
                    .flatMap { if (answer == ANSWER_SKIP) nextQuestion() else Single.just(it) }
        } else {
            nextQuestion()
        }
    }

    private fun nextQuestion(): Single<Question> {
        var curr = findUnansweredQuestion()
        if (curr != null) {
            currQuestion = curr
            return Single.just(currQuestion)
        }

        if (questions.isNotEmpty()) {
            return questionsRepository.postAnswers(questions)
                    .doOnComplete {
                        questions = emptyList()
                    }.andThen(getQuestions())
        }

        return getQuestions()
    }

    private fun getQuestions(): Single<Question> {
        return questionsRepository.getQuestions()
                .doOnSuccess {
                    questions = it
                    currQuestion = findUnansweredQuestion()
                    if (currQuestion == null) {
                        currQuestion = it[it.size - 1]
                    }
                }
                .flatMap { Single.just(currQuestion) }
    }

    private fun findUnansweredQuestion(): Question? {
        return questions.find { it.answer == ANSWER_NO }
    }
}
