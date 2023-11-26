package com.example.cureme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Login: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        val inputName = findViewById<EditText>(R.id.InputName)
        val inputEmail = findViewById<EditText>(R.id.InputEmailId)
        val inputPassword = findViewById<EditText>(R.id.InputPass)
        val btnLogin = findViewById<Button>(R.id.BtnLogin)
        val goToSignUp = findViewById<TextView>(R.id.GoToSignUp)
        val forgotPassword = findViewById<TextView>(R.id.InputDiffPass)

        btnLogin.setOnClickListener {
            //val name = inputName.text.toString()
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val i = Intent(this, Medicine::class.java)
                            startActivity(i)
                            finish()
                        } else {
                            Toast.makeText(this, "Login failed. Please try again.", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        goToSignUp.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        forgotPassword.setOnClickListener {
            val email= inputEmail.text.toString() // Getting the email of the current user
            if (email!= null) {
                // Sending a password reset email to the user's email address
                Firebase.auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // If the password reset email is sent successfully, show a success message
                            Toast.makeText(this, "Password reset email sent successfully.", Toast.LENGTH_SHORT).show()
                        } else {
                            // If the password reset email fails to send, show an error message
                            Toast.makeText(this, "Failed to send password reset email.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
/*
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val i = Intent(this, Medicine::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(this, "Login failed. Please try again.", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }*/
}

