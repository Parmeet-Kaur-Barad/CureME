package com.example.cureme


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        val t1 = findViewById<TextView>(R.id.txt1)
        val t2 = findViewById<TextView>(R.id.txt2)
        val uname = findViewById<EditText>(R.id.InputUsername)
        val umail = findViewById<EditText>(R.id.InputEmail)
        val upass = findViewById<EditText>(R.id.InputPassword)
        val re = findViewById<EditText>(R.id.InputConfimPassword)
        val b1 = findViewById<Button>(R.id.btnSignUp)
        val gotot1 = findViewById<TextView>(R.id.GoToLogin)

        b1.setOnClickListener {
            val mail = umail.text.toString()
            val pass = upass.text.toString()
            if (mail.isNotEmpty() && pass.isNotEmpty()) {
                signUp(mail, pass)
            } else {
                Toast.makeText(this, "Please Enter Valid Email and Password", Toast.LENGTH_LONG)
                    .show()
            }
        }
        gotot1.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { verificationTask ->
                            if (verificationTask.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Registration Successful. Please Check Email for Verification",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Email Verification failed, Please Try Again",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                }
            }
    }
}
