package com.besugos.desafio4dha.splash.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.besugos.desafio4dha.R
import com.besugos.desafio4dha.auth.view.LoginActivity
import com.besugos.desafio4dha.home.view.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({

            val pref = baseContext.getSharedPreferences(user?.email, Context.MODE_PRIVATE)
            val persist = pref.getBoolean(user?.email, false)

            if (auth.currentUser != null && persist) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 1000)
    }
}
