package com.jaloveast1k.thisorthat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jaloveast1k.thisorthat.App
import com.jaloveast1k.thisorthat.R

class MainFragment : MVVMFragment() {
    private val userListViewModel = App.injectUserListViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.users_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
//        subscribe(userListViewModel.getUsers()
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    Timber.d("Received UIModel with ${it.users.size} users.")
//                    showUsers(it)
//                }, {
//                    Timber.w(it)
//                    showError()
//                }))
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
