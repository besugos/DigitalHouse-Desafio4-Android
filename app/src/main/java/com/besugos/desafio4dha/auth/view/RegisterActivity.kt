package com.besugos.desafio4dha.auth.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.besugos.desafio4dha.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    private lateinit var tfEmail: TextInputLayout
    private lateinit var tfName: TextInputLayout
    private lateinit var tfRptPass: TextInputLayout
    private lateinit var tfPass: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etName: TextInputEditText
    private lateinit var etRptPass: TextInputEditText
    private lateinit var etPass: TextInputEditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tfEmail = findViewById(R.id.tfEmailRegister)
        tfPass = findViewById(R.id.tfPassRegister)
        tfName = findViewById(R.id.tfNameRegister)
        tfRptPass = findViewById(R.id.tfRptPassRegister)
        etEmail = findViewById(R.id.etEmailRegister)
        etPass = findViewById(R.id.etPassRegister)
        etName = findViewById(R.id.etNameRegister)
        etRptPass = findViewById(R.id.etRptPassRegister)

        auth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.btnRegister).setOnClickListener() {
            if (validaCampos()) {
                criaConta(etName.text.toString(), etEmail.text.toString(), etPass.text.toString())
            }
        }

        etEmail.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfEmail.error = ""
            }
        })

        etPass.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfPass.error = ""
            }
        })

        etName.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfName.error = ""
            }
        })

        etRptPass.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfRptPass.error = ""
            }
        })




    }

    private fun validaCampos(): Boolean {
        var response = true

        if (etEmail.text.isNullOrBlank()) {
            tfEmail.error = "Please type your e-mail"
            response = false
        }

        if (etName.text.isNullOrBlank()) {
            tfName.error = "Please type your Name"
            response = false
        }

        if (etPass.text.isNullOrBlank()) {
            tfPass.error = "Password Required"
            response = false
        }

//        else if (etPass.text!!.length < 8){
//            tfPass.error = "Password must be at least 8 characters long"
//            response = false
//        }

        if (etRptPass.text.isNullOrBlank()) {
            tfRptPass.error = "Please repeat your password"
            response = false
        } else if (etPass.text.toString() != etRptPass.text.toString()){
            tfPass.error = "Passwords do not match"
            response = false
        }

        return response
    }

    private fun criaConta(nome: String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "User successfully created",
                            Toast.LENGTH_SHORT
                        ).show()
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}