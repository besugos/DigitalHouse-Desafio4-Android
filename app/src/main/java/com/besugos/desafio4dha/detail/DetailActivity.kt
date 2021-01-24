package com.besugos.desafio4dha.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.besugos.desafio4dha.R
import com.besugos.desafio4dha.edit.view.EditActivity
import com.besugos.desafio4dha.home.view.HomeActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        findViewById<FloatingActionButton>(R.id.fabEdit).setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)


        }
    }
}