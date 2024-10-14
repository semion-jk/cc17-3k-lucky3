package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class OrderConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_confirmation_screen)

        val backorder = findViewById<Button>(R.id.back_button)

        backorder.setOnClickListener {
            val intent = Intent(this, OrderScreenActivity::class.java)
            startActivity(intent)
        }
        val confirm = findViewById<Button>(R.id.confirm_button)

        confirm.setOnClickListener {
            val intent = Intent(this, TrackingActivity::class.java)
            startActivity(intent)
        }
    }
}