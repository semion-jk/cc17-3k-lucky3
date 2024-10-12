package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)

        val login = findViewById<Button>(R.id.loginText)

        login.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        val sign = findViewById<Button>(R.id.signupButton)

        sign.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
}
}