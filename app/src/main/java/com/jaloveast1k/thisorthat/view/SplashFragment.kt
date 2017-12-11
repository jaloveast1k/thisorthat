package com.jaloveast1k.thisorthat.view

import android.view.View
import com.jaloveast1k.thisorthat.R
import com.jaloveast1k.thisorthat.dagger.ControllerModule
import com.jaloveast1k.thisorthat.view.activities.MainActivity
import com.jaloveast1k.thisorthat.view.activities.openActivity
import com.jaloveast1k.thisorthat.viewmodel.SplashViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_splash.*
import timber.log.Timber
import javax.inject.Inject

class SplashFragment : BaseFragment() {
//    private val component by lazy { app.component.plus(ControllerModule(this)) }

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun setLayoutRes(): Int = R.layout.fragment_splash

    override fun initUI() {
        retry.setOnClickListener({
            registration()
            retry.visibility = View.GONE
        })
    }

    override fun onStart() {
        super.onStart()

//        component.inject(this)

        registration()
    }

    private fun registration() {
        subscribe(splashViewModel.registration()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Received ${it.token} for user ${it.user}")
                    moveNext()
                }, {
                    Timber.d(it)
                    showError()
                }))
    }

    private fun moveNext() {
        baseActivity?.openActivity(MainActivity::class.java, finishThis = true)
    }

    private fun showError() {
        showMessage(R.string.error_connection)
        retry.visibility = View.VISIBLE
    }
}
