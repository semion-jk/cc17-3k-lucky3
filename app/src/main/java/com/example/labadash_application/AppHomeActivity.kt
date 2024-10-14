package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AppHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_home_page)

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
        val tracking = findViewById<ImageView>(R.id.mapIcon)

        tracking.setOnClickListener {
            val intent = Intent(this, TrackingActivity::class.java)
            startActivity(intent)
        }

        val menu = findViewById<ImageView>(R.id.menuIcon)

        menu.setOnClickListener {
            val intent = Intent(this, ProfileSettingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.right_animation, R.anim.left_animation)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_animation, R.anim.left_animation)
    }
}