package com.besugos.desafio4dha.splash.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.besugos.desafio4dha.MainActivity
import com.besugos.desafio4dha.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        private lateinit var auth: FirebaseAuth
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//
//            setContentView(R.layout.activity_splash)
//
//            auth = FirebaseAuth.getInstance()
//
//            Handler(Looper.getMainLooper()).postDelayed({
//
//                if (auth.currentUser == null) {
//                    val intent = Intent(this, LoginActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            }, 2000)


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)


    }

}
