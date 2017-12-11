package com.jaloveast1k.thisorthat.view

import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jaloveast1k.thisorthat.App
import com.jaloveast1k.thisorthat.dagger.ControllerModule
import com.jaloveast1k.thisorthat.view.activities.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {
    private val subscriptions = CompositeDisposable()

    protected var screenWidth = 0
    protected var screenHeight = 0

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

        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = Math.min(size.x, size.y)
        screenHeight = Math.max(size.x, size.y)

        return view
    }

    override fun onStart() {
        super.onStart()

        if (App.fragmentClass != this.javaClass) {
            App.controllerComponent = app.component.plus(ControllerModule(this))
            App.fragmentClass = this.javaClass
        }
        when (this) {
            is MainFragment -> App.controllerComponent?.inject(this)
            is SplashFragment -> App.controllerComponent?.inject(this)
        }

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

    val BaseFragment.app: App
        get() = activity.application as App

    val BaseFragment.baseActivity: BaseActivity?
        get() = activity as? BaseActivity
}
