package com.besugos.desafio4dha.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.besugos.desafio4dha.R
import com.besugos.desafio4dha.edit.view.AddActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        findViewById<FloatingActionButton>(R.id.fabEdit).setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)


        }
    }
}