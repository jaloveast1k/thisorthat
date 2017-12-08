package com.jaloveast1k.thisorthat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jaloveast1k.thisorthat.view.SplashFragment

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frag_container, SplashFragment()).commit()
        }
    }
}
