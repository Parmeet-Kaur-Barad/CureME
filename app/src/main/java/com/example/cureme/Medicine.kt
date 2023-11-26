package com.example.cureme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Medicine : AppCompatActivity() {
    private lateinit var medTextView: TextView
    private lateinit var timeTextView: TextView
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine)

        var b1 = findViewById<FloatingActionButton>(R.id.add)
        medTextView = findViewById<TextView>(R.id.medTextView)
        timeTextView = findViewById<TextView>(R.id.timeTextView)

    // Get the input data from the intent
        val med = intent.getStringExtra("med")
        val time = intent.getStringExtra("time")

    // Set the input data to the text views
        medTextView.text = "Medicine Name: ${med.orEmpty()}"
        timeTextView.text = "Set Time: ${time.orEmpty()}"


        b1.setOnClickListener {
            val i = Intent(this,setreminder1::class.java)
            startActivity(i)
        }
    }
}
