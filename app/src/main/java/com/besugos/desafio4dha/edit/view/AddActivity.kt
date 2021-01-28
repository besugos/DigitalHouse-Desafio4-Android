package com.besugos.desafio4dha.edit.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.besugos.desafio4dha.R
import com.besugos.desafio4dha.home.model.GameModel
import com.besugos.desafio4dha.home.view.HomeActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddActivity : AppCompatActivity() {
    private var hasImage: Boolean = false
    private var imageURI: Uri? = null
    private lateinit var etName: TextInputEditText
    private lateinit var etYear: TextInputEditText
    private lateinit var etDesc: TextInputEditText
    private lateinit var auth: FirebaseAuth
    private lateinit var filename: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        etName = findViewById<TextInputEditText>(R.id.etGameName)
        etYear = findViewById<TextInputEditText>(R.id.etGameYear)
        etDesc = findViewById<TextInputEditText>(R.id.etGameDesc)

        findViewById<CardView>(R.id.cvRoundPic).setOnClickListener {
            procurarArquivo()
        }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            enviarArquivo()
        }
    }

    fun procurarArquivo() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, CONTENT_REQUEST_CODE)
    }

    fun enviarArquivo() {
        if (hasImage) {
            val stamp = System.currentTimeMillis().toString()
            imageURI?.run {
                val firebase = FirebaseStorage.getInstance()
                val storage = firebase.getReference("uploads")

                val extension = MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(contentResolver.getType(imageURI!!))
                filename = "${stamp}.${extension}"
                val fileReference = storage.child("${stamp}.${extension}")

                fileReference.putFile(this)
                    .addOnSuccessListener {
                        // Salvar o fileReference.toString() no Realtime Database
                        Toast.makeText(this@AddActivity, "Saving...", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@AddActivity, "Saving...", Toast.LENGTH_SHORT).show()
                    }

            }

            val game = GameModel(
                etName.text.toString(),
                etYear.text.toString(),
                etDesc.text.toString(),
                filename
            )

            auth = FirebaseAuth.getInstance()

            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference(auth.currentUser!!.uid)

            myRef.child(stamp).setValue(game)

            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@AddActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }, 1000)
        } else {
            Toast.makeText(this@AddActivity, "Saving failed. You must provide an image.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CONTENT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Código
            hasImage = true
            imageURI = data?.data
            findViewById<ImageView>(R.id.imgLoadedGame).setImageURI(imageURI)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@AddActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val CONTENT_REQUEST_CODE = 1
    }
}