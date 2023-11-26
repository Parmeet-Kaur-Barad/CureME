package com.example.cureme

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.google.firebase.firestore.FirebaseFirestore


class setreminder1 : AppCompatActivity() {
    private lateinit var inputMed: EditText
    private lateinit var inputTime: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var checkBox4: CheckBox
    private lateinit var checkBox5: CheckBox
    private lateinit var checkBox6: CheckBox
    private lateinit var checkBox7: CheckBox
    private lateinit var btn: Button
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setreminder1)

        createNotificationChannel()

        val db = FirebaseFirestore.getInstance()
        val selectTimeButton: Button = findViewById(R.id.selectTimeButton)
        inputMed = findViewById(R.id.InputMed)
        inputTime = findViewById(R.id.InputTime)
        checkBox = findViewById(R.id.CheckBox)
        checkBox1 = findViewById(R.id.CheckBox1)
        checkBox2 = findViewById(R.id.CheckBox2)
        checkBox3 = findViewById(R.id.CheckBox3)
        checkBox4 = findViewById(R.id.CheckBox4)
        checkBox5 = findViewById(R.id.CheckBox5)
        checkBox6 = findViewById(R.id.CheckBox6)
        checkBox7 = findViewById(R.id.CheckBox7)
        btn = findViewById(R.id.Btn)

        selectTimeButton.setOnClickListener {
            // Code to be executed when the button is clicked
            selectTime()
        }

        btn.setOnClickListener { view: View ->
            val med = inputMed.text.toString()
            val time = inputTime.text.toString()
            val everyday = checkBox.isChecked
            val s = checkBox1.isChecked
            val m = checkBox2.isChecked
            val t = checkBox3.isChecked
            val w = checkBox4.isChecked
            val th = checkBox5.isChecked
            val f = checkBox6.isChecked
            val s2 = checkBox7.isChecked

            // Create an intent for the broadcast receiver
            val intent = Intent(this, ReminderBroadcastReceiver::class.java)

            // Add any extra data to the intent
            intent.putExtra("medication", med)
            intent.putExtra("time", time)
            intent.putExtra("everyday", everyday)
            intent.putExtra("s", s)
            intent.putExtra("m", m)
            intent.putExtra("t", t)
            intent.putExtra("w", w)
            intent.putExtra("th", th)
            intent.putExtra("f", f)
            intent.putExtra("s2", s2)

            // Send the broadcast
            sendBroadcast(intent, null)

            // Create and schedule the notification
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notification = NotificationCompat.Builder(this, "reminder_channel")
                .setContentTitle("Reminder")
                .setContentText("It's time to take your medication!")
                .build()
            val notificationId = System.currentTimeMillis().toInt() // Unique ID for each notification
            notificationManager.notify(notificationId, notification)

            // Log the data before sending the broadcast (optional)
            Log.d("setreminder", "Medication Name: $med")
            Log.d("setreminder", "Set Time: $time")
            Log.d("setreminder", "Everyday: $everyday")
            Log.d("setreminder", "S: $s")
            Log.d("setreminder", "M: $m")
            Log.d("setreminder", "T: $t")
            Log.d("setreminder", "W: $w")
            Log.d("setreminder", "Th: $th")
            Log.d("setreminder", "F: $f")
            Log.d("setreminder", "S: $s2")

            val i = Intent(this, Medicine::class.java)
            i.putExtra("med", med)
            i.putExtra("time", time)
            startActivity(i)
            finish()

            val reminderData = hashMapOf(
                "medication" to med,
                "time" to time,
                "everyday" to everyday,
                "s" to s,
                "m" to m,
                "t" to t,
                "w" to w,
                "th" to th,
                "f" to f,
                "s2" to s2
            )

            db.collection("reminders")
                .add(reminderData)
                .addOnSuccessListener { documentReference ->
                    Log.d("setreminder", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("setreminder", "Error adding document", e)
                }
            db.collection("reminders")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("setreminder", "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("setreminder", "Error getting documents.", exception)
                }
        }
    }


    private fun selectTime() {
        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                updateSelectedTime()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }

    private fun updateSelectedTime() {
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        inputTime.setText(format.format(calendar.time))
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "reminder_channel",
                "Reminder Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
