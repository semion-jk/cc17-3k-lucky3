package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AccountPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_page_screen)

        val back = findViewById<ImageButton>(R.id.backButton)

        back.setOnClickListener {
            val intent = Intent(this, ProfileMenuActivity::class.java)
            startActivity(intent)
        }

    }
}