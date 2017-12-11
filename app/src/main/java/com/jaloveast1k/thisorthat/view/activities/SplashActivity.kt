package com.jaloveast1k.thisorthat.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.jaloveast1k.thisorthat.R
import com.jaloveast1k.thisorthat.repository.data.Consts
import com.jaloveast1k.thisorthat.repository.data.PreferencesHelper
import com.jaloveast1k.thisorthat.view.SplashFragment

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: грязный хак, но мне пока лень :)
        val prefs = PreferencesHelper(this)
        val token: String? = prefs.getValue(Consts.SharedPrefs.TOKEN)
        if (token != null) {
            finish()
            openActivity(MainActivity::class.java)
            return
        }

        setupFullScreen()

        setContentView(R.layout.activity_single_fragment)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frag_container, SplashFragment()).commit()
        }
    }
}
