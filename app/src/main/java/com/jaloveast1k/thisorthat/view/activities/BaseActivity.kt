package com.jaloveast1k.thisorthat.view.activities

import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager

open class BaseActivity : AppCompatActivity() {
    protected fun setupFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}