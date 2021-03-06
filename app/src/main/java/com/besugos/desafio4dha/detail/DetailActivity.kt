package com.besugos.desafio4dha.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.besugos.desafio4dha.R
import com.besugos.desafio4dha.edit.view.EditActivity
import com.besugos.desafio4dha.home.view.HomeActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val name = intent.getStringExtra("NAME")
        val year = intent.getStringExtra("YEAR")
        val desc = intent.getStringExtra("DESC")
        val pic = intent.getStringExtra("PIC")

        val txtLarge = findViewById<TextView>(R.id.txtGameTitleLarge)
        val txtSmall = findViewById<TextView>(R.id.txtGameTitleSmall)
        val txtYear = findViewById<TextView>(R.id.txtLaunchDate)
        val txtDesc = findViewById<TextView>(R.id.txtGameDescription)
        val imgLarge = findViewById<ImageView>(R.id.imgGameLarge)
        val imgBack = findViewById<ImageView>(R.id.imgBack)

        txtLarge.text = name
        txtSmall.text = name
        txtYear.text = "Lançamento: $year"
        txtDesc.text = desc

        val firebase = FirebaseStorage.getInstance()
        val storage = firebase.getReference("uploads")

        storage.child(pic!!).downloadUrl.addOnSuccessListener {
            Picasso.get().load(it).into(imgLarge)
        }

        imgBack.setOnClickListener {
            val intent = Intent(this@DetailActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<FloatingActionButton>(R.id.fabEdit).setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            with(intent) {
                putExtra("NAME", name)
                putExtra("DESC", desc)
                putExtra("YEAR", year)
                putExtra("PIC", pic)
                startActivity(this)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@DetailActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}