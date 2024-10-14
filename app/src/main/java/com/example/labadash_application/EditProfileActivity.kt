package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page_screen)

        val back = findViewById<ImageButton>(R.id.backButton)

        back.setOnClickListener {
            val intent = Intent(this, ProfileMenuActivity::class.java)
            startActivity(intent)
        }

        val backhome = findViewById<TextView>(R.id.doneButton)

        backhome.setOnClickListener {
            val intent = Intent(this, AppHomeActivity::class.java)
            startActivity(intent)
        }


    }
}