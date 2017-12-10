package com.jaloveast1k.thisorthat.view

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jaloveast1k.thisorthat.App
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class MVVMFragment : Fragment() {
    private val subscriptions = CompositeDisposable()

    fun subscribe(disposable: Disposable): Disposable {
        subscriptions.add(disposable)
        return disposable
    }

    override fun onStop() {
        super.onStop()
        subscriptions.clear()
    }

    protected abstract fun setLayoutRes(): Int

    protected abstract fun initUI()

    @SuppressWarnings("RestrictedApi")
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View?

        view = if (getFragmentTheme() == -1) {
            inflater!!.inflate(setLayoutRes(), null)
        } else {
            val contextThemeWrapper = ContextThemeWrapper(activity, getFragmentTheme())
            val localInflater = inflater!!.cloneInContext(contextThemeWrapper)
            localInflater.inflate(setLayoutRes(), container, false)
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        initUI()
    }

    protected fun getFragmentTheme(): Int {
        return -1
    }

    protected fun showMessage(text: Any?) {
        if (!isAdded) return

        var actualText: String?
        when (text) {
            is String? -> actualText = text
            is Int -> actualText = getString(text)
            else -> actualText = null
        }
        if (actualText != null)
            Toast.makeText(activity, actualText, Toast.LENGTH_SHORT).show()
    }

    protected fun <T : Any> openActivity(clazz: Class<T>, params: ContentValues? = null, requestCode: Int = -1, finishThis: Boolean = false) {
        if (!isAdded) return

        val i = Intent()
        i.setClass(activity, clazz)

        if (params != null) {
            for (key in params.keySet()) {
                val value = params.get(key)
                when (value) {
                    is String -> i.putExtra(key, value)
                    is Int -> i.putExtra(key, value)
                    is Boolean -> i.putExtra(key, value)
                    is Double -> i.putExtra(key, value)
                    is Float -> i.putExtra(key, value)
                    else -> throw UnsupportedOperationException("Wrong type")
                }
            }
        }

        if (finishThis) {
            activity.finish()
        }

        if (requestCode != -1) {
            startActivityForResult(i, requestCode)
        } else {
            startActivity(i)
        }
    }

    val MVVMFragment.app: App
        get() = activity.application as App
}
