package com.jaloveast1k.thisorthat.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.RotateAnimation
import com.jaloveast1k.thisorthat.App
import com.jaloveast1k.thisorthat.R
import com.jaloveast1k.thisorthat.R.id.*
import com.jaloveast1k.thisorthat.dagger.ControllerModule
import com.jaloveast1k.thisorthat.repository.data.Consts
import com.jaloveast1k.thisorthat.repository.data.Consts.General.ANSWER_DOWN
import com.jaloveast1k.thisorthat.repository.data.Consts.General.ANSWER_NO
import com.jaloveast1k.thisorthat.repository.data.Consts.General.ANSWER_SKIP
import com.jaloveast1k.thisorthat.repository.data.Consts.General.ANSWER_UP
import com.jaloveast1k.thisorthat.repository.data.Consts.UI.SMALL_TEXT_SIZE_TO_WIDTH
import com.jaloveast1k.thisorthat.repository.data.Consts.UI.TEXT_SIZE_TO_WIDTH
import com.jaloveast1k.thisorthat.repository.data.Consts.UI.TITLE_TEXT_SIZE_TO_WIDTH
import com.jaloveast1k.thisorthat.repository.data.Consts.UI.VERY_SMALL_TEXT_SIZE_TO_WIDTH
import com.jaloveast1k.thisorthat.repository.data.Question
import com.jaloveast1k.thisorthat.utils.MaterialInterpolator
import com.jaloveast1k.thisorthat.viewmodel.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber
import javax.inject.Inject

class MainFragment : BaseFragment() {
    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun setLayoutRes(): Int = R.layout.fragment_main

    override fun initUI() {
        diagramm.setOrientation(resources.configuration.orientation)

        info.setOnClickListener {
            rotateAnimation(infoIcon)

            infoIcon.postDelayed({
                // TODO: open about activity
            }, 300)
        }

        skip.setOnClickListener({
            rotateAnimation(skipIcon)

            showMessage(R.string.question_skipped)

            answerClick(ANSWER_SKIP)
        })

        account.setOnClickListener{
            // TODO: open account activity
        }

        add.setOnClickListener {
            // TODO: open add question activity
        }

        share.setOnClickListener {
            // showShareDialog()
        }

        answerUp.setOnClickListener {
            answerClick(ANSWER_UP)
        }

        answerDown.setOnClickListener {
            answerClick(ANSWER_DOWN)
        }

        val size = screenWidth / TEXT_SIZE_TO_WIDTH
        val titleSize: Float = screenWidth / TITLE_TEXT_SIZE_TO_WIDTH
        val percentSize = screenWidth / SMALL_TEXT_SIZE_TO_WIDTH
        val notModeratedSize = screenWidth / VERY_SMALL_TEXT_SIZE_TO_WIDTH
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize)
        textDown.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        textUp.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        textUpVote.setTextSize(TypedValue.COMPLEX_UNIT_PX, percentSize)
        textDownVote.setTextSize(TypedValue.COMPLEX_UNIT_PX, percentSize)
        textUpCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize)
        textDownCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize)
        textNotModerated.setTextSize(TypedValue.COMPLEX_UNIT_PX, notModeratedSize)
    }

    override fun onStart() {
        super.onStart()

        subscribe(mainViewModel.getQuestion()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Received question $it")
                    displayQuestion(it)
                }, {
                    Timber.w(it)
                    showMessage(R.string.error_refresh)
                }))
    }

    private fun displayQuestion(question: Question) {
        textUp.text = question.textUp
        textDown.text = question.textDown
        textNotModerated.visibility =
                if (question.moderate == Consts.General.QUESTION_MODERATED) View.GONE
                else View.VISIBLE

        var textVisibility = View.VISIBLE
        var skipVisibility = View.GONE

        if (question.answer != ANSWER_NO) {
            diagramm.setUpPercents(question.percentDown())
            textUpVote.text = "${question.percentUp()}%"
            textDownVote.text = "${question.percentDown()}%"
            textUpCount.text = "${question.voteUp}"
            textDownCount.text = "${question.voteDown}"

            playAnimation()
        } else {
            textVisibility = View.GONE
            skipVisibility = View.VISIBLE
        }

        textUpVote.visibility = textVisibility
        textDownVote.visibility = textVisibility
        textUpCount.visibility = textVisibility
        textDownCount.visibility = textVisibility
        diagramm.visibility = textVisibility
        skip.visibility = skipVisibility
        skipIcon.visibility = skipVisibility
    }

    private fun rotateAnimation(view: View) {
        val rotate = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 500
        rotate.interpolator = MaterialInterpolator()
        view.startAnimation(rotate)
    }

    private fun answerClick(answer: String) {
        subscribe(mainViewModel.answer(answer)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Answered $it")

                    displayQuestion(it)
                }, {
                    Timber.w(it)
                    showMessage(R.string.error_answer)
                }))
    }

    private fun playAnimation() {
        val alphaTime: Long = 1000
        val bounceTime: Long = 500

        val animationAlpha1 = ObjectAnimator.ofFloat(textUpVote, "alpha", 0f, 1f)
        animationAlpha1.duration = alphaTime

        val animationAlpha2 = ObjectAnimator.ofFloat(textDownVote, "alpha", 0f, 1f)
        animationAlpha2.duration = alphaTime

        val animationAlpha3 = ObjectAnimator.ofFloat(textUpCount, "alpha", 0f, 1f)
        animationAlpha3.duration = alphaTime

        val animationAlpha4 = ObjectAnimator.ofFloat(textDownCount, "alpha", 0f, 1f)
        animationAlpha4.duration = alphaTime

        val animationBounceX = ObjectAnimator.ofFloat(diagramm, "scaleX", 0f, 1f)
        animationBounceX.duration = bounceTime
        animationBounceX.interpolator = BounceInterpolator()

        val animationBounceY = ObjectAnimator.ofFloat(diagramm, "scaleY", 0f, 1f)
        animationBounceY.duration = bounceTime
        animationBounceY.interpolator = BounceInterpolator()

        val all = AnimatorSet()
        all.playTogether(animationAlpha1, animationAlpha2, animationAlpha3, animationAlpha4, animationBounceX, animationBounceY)
        all.start()
    }
}
