package com.jaloveast1k.thisorthat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jaloveast1k.thisorthat.App
import com.jaloveast1k.thisorthat.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SplashFragment : MVVMFragment() {
    private val splashViewModel = App.injectSplashViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onStart() {
        super.onStart()
        subscribe(splashViewModel.registration()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Received ${it.token} for user ${it.user}")
                    moveNext()
                }, {
                    showError()
                }))
    }

    private fun moveNext() {

    }

//    private fun showUsers(data: UsersList) {
//        when (data.error) {
//            null -> usersList.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, data.users)
//            is ConnectException -> Timber.d("No connection, maybe inform user that data loaded from DB.")
//            else -> showError()
//        }
//    }

    private fun showError() {
        Toast.makeText(context, "An error occurred :(", Toast.LENGTH_SHORT).show()
    }
}
