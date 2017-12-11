package com.jaloveast1k.thisorthat.view.activities

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager

open class BaseActivity : AppCompatActivity() {
    /*
    to make activity fullscreen, has to be called before setContentView
     */
    protected fun setupFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}

/*
extension function to launch an activity
*/
fun <T : Any> BaseActivity.openActivity(clazz: Class<T>, params: ContentValues? = null, requestCode: Int = -1, finishThis: Boolean = false) {
    val i = Intent()
    i.setClass(this, clazz)

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
        this.finish()
    }

    if (requestCode != -1) {
        startActivityForResult(i, requestCode)
    } else {
        startActivity(i)
    }
}