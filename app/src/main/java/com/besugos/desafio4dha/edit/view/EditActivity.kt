package com.besugos.desafio4dha.edit.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.besugos.desafio4dha.R
import com.besugos.desafio4dha.detail.DetailActivity
import com.besugos.desafio4dha.home.model.GameModel
import com.besugos.desafio4dha.home.view.HomeActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class EditActivity : AppCompatActivity() {

    private var imageURI: Uri? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var filename: String
    private lateinit var pic: String

    private lateinit var txtName: TextInputEditText
    private lateinit var txtYear: TextInputEditText
    private lateinit var txtDesc: TextInputEditText
    private lateinit var btnSave: Button
    private lateinit var imgTop: ImageView
    private lateinit var card: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        txtName = findViewById<TextInputEditText>(R.id.etGameNameEdit)
        txtYear = findViewById<TextInputEditText>(R.id.etGameYearEdit)
        txtDesc = findViewById<TextInputEditText>(R.id.etGameDescEdit)
        btnSave = findViewById<Button>(R.id.btnSaveEdit)
        imgTop = findViewById<ImageView>(R.id.imgLoadedGameEdit)
        card = findViewById<CardView>(R.id.cvRoundPicEdit)

        val name = intent.getStringExtra("NAME")
        val year = intent.getStringExtra("YEAR")
        val desc = intent.getStringExtra("DESC")
        pic = intent.getStringExtra("PIC")!!

        txtName.setText(name)
        txtYear.setText(year)
        txtDesc.setText(desc)

        val firebase = FirebaseStorage.getInstance()
        val storage = firebase.getReference("uploads")

        storage.child(pic).downloadUrl.addOnSuccessListener {
            Picasso.get().load(it).into(imgTop)
            imageURI = it
        }

        card.setOnClickListener {
            procurarArquivo()
        }

        findViewById<Button>(R.id.btnSaveEdit).setOnClickListener {
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
        Log.d("TAG", "Entrou na Função")
        imageURI?.run {
            Log.d("TAG", "Entrou em imageURI")
            val firebase = FirebaseStorage.getInstance()
            val storage = firebase.getReference("uploads")

            val extension = MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(contentResolver.getType(imageURI!!))
            filename = pic
            val fileReference = storage.child(filename)

            fileReference.putFile(this)
                .addOnSuccessListener {
                    // Salvar o fileReference.toString() no Realtime Database
                    Toast.makeText(this@EditActivity, "Saving...", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener {
                    Toast.makeText(this@EditActivity, "Saving...", Toast.LENGTH_SHORT).show()
                }
        }

        val game = GameModel(
            txtName.text.toString(),
            txtYear.text.toString(),
            txtDesc.text.toString(),
            filename
        )

        auth = FirebaseAuth.getInstance()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(auth.currentUser!!.uid)

        val ponto = "."
        val filhos = pic.split(ponto)
        val filho = filhos[0]

        myRef.child(filho).setValue(game)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@EditActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CONTENT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Código
            imageURI = data?.data
            findViewById<ImageView>(R.id.imgLoadedGameEdit).setImageURI(imageURI)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@EditActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val CONTENT_REQUEST_CODE = 1
    }
}
