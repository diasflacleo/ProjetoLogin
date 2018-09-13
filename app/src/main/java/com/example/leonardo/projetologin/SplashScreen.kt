package com.example.leonardo.projetologin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.schedule

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        supportActionBar?.hide();
    }

    override fun onStart() {
        super.onStart()
        Timer("endSplash", false).schedule(3000) {
            val intent = Intent(this@SplashScreen, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}