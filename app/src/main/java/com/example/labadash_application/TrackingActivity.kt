package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TrackingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tracking_screen)

        val laundry = findViewById<ImageButton>(R.id.imageButton)

        laundry.setOnClickListener {
            val intent = Intent(this, OrderScreenActivity::class.java)
            startActivity(intent)
        }
        val laundry2 = findViewById<TextView>(R.id.buttonText)

        laundry.setOnClickListener {
            val intent = Intent(this, OrderScreenActivity::class.java)
            startActivity(intent)
        }

        val home = findViewById<ImageView>(R.id.homeIcon)

        home.setOnClickListener {
            val intent = Intent(this, AppHomeActivity::class.java)
            startActivity(intent)
        }

        val menu = findViewById<ImageView>(R.id.menuIcon)

        menu.setOnClickListener {
            val intent = Intent(this, ProfileMenuActivity::class.java)
            startActivity(intent)
        }

    }
}