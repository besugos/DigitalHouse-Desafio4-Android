package com.besugos.desafio4dha.auth.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.besugos.desafio4dha.MainActivity
import com.besugos.desafio4dha.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var tfEmail: TextInputLayout
    private lateinit var tfPass: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPass: TextInputEditText
    private lateinit var cbPersist: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val btnLogin = this.findViewById<Button>(R.id.btnLogin)
        val btnRegister = this.findViewById<TextView>(R.id.txtCreateAccount)

        tfEmail = findViewById(R.id.tfEmailLogin)
        tfPass = findViewById(R.id.tfPassLogin)
        etEmail = findViewById(R.id.etEmailLogin)
        etPass = findViewById(R.id.etPassLogin)
        cbPersist = findViewById(R.id.cbPersist)

        btnLogin.setOnClickListener() {

            if (validaCampos()) {
                auth.signInWithEmailAndPassword(etEmail.text.toString(), etPass.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success")
                            val user = auth.currentUser
                            val pref = baseContext.getSharedPreferences(user?.email, Context.MODE_PRIVATE)
                            pref.edit().putBoolean(user?.email, cbPersist.isChecked).apply()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Fail",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        btnRegister.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfEmail.error = ""
            }
        })

        etPass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfEmail.error = ""
            }
        })

    }

    private fun validaCampos(): Boolean {
        var response = true

        if (etEmail.text.isNullOrBlank()) {
            tfEmail.error = "Please type your e-mail"
            response = false
        }

        if (etPass.text.isNullOrBlank()) {
            tfPass.error = "Please type your password"
            response = false
        } else if (etPass.text!!.length < 8){
            tfPass.error = "Password must be at least 8 characters long"
            response = false
        }

        return response
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Valide seu email", Toast.LENGTH_LONG).show()
                auth.signOut()
            }
        }

    }

    companion object {
        private const val RC_SIGN_IN = 1
        private const val FB_SIGN_IN = 64206
    }
}