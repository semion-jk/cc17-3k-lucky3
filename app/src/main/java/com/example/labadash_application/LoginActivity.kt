package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val signup = findViewById<Button>(R.id.sign_up)

        signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        val donelogin = findViewById<Button>(R.id.login_done_button)

        donelogin.setOnClickListener {
            val intent = Intent(this, welcomeActivity::class.java)
            startActivity(intent)
        }
    }
}