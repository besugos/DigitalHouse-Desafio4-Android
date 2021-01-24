package com.besugos.desafio4dha.edit.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.besugos.desafio4dha.R
import com.besugos.desafio4dha.model.GameModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class EditActivity : AppCompatActivity() {

    private var imageURI: Uri? = null
    private lateinit var etName: TextInputEditText
    private lateinit var etYear: TextInputEditText
    private lateinit var etDesc: TextInputEditText
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        etName = findViewById<TextInputEditText>(R.id.etGameName)
        etYear = findViewById<TextInputEditText>(R.id.etGameYear)
        etDesc = findViewById<TextInputEditText>(R.id.etGameDesc)


//        // Write a message to the database

//
//        myRef.setValue("Hello, World!")
//
//        obterArquivo()

        findViewById<ImageView>(R.id.imgCircle).setOnClickListener {
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

    fun obterArquivo() {
        val firebase = FirebaseStorage.getInstance()
        val storage = firebase.getReference("uploads")

        storage.child("in_game.png").downloadUrl.addOnSuccessListener {
            Picasso.get().load(it).into(findViewById<ImageView>(R.id.imgPic))
        }
    }


    fun enviarArquivo() {
        val stamp = System.currentTimeMillis().toString()
        imageURI?.run {
            val firebase = FirebaseStorage.getInstance()
            val storage = firebase.getReference("uploads")

            val extension = MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(contentResolver.getType(imageURI!!))

            val fileReference = storage.child("${stamp}.${extension}")

            fileReference.putFile(this)
                .addOnSuccessListener {
                    // Salvar o fileReference.toString() no Realtime Database
                    Toast.makeText(this@EditActivity, "Sucesso!", Toast.LENGTH_SHORT).show()
                    Log.d("PROGRESS", fileReference.toString())
                }
                .addOnFailureListener {
                    Toast.makeText(this@EditActivity, "DEU ÁGUIA!", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener {
                    Log.d("PROGRESS", it.toString())
                }
        }

        val game = GameModel(etName.text.toString(), etYear.text.toString(), etDesc.text.toString())

        auth = FirebaseAuth.getInstance()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(auth.currentUser!!.uid)

        myRef.child(stamp).setValue(game)

        finish()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CONTENT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Código
            imageURI = data?.data
            findViewById<ImageView>(R.id.imgPic).setImageURI(imageURI)
        }
    }

    companion object {
        const val CONTENT_REQUEST_CODE = 1
    }
}