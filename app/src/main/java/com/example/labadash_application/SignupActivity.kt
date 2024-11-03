package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)

        val login = findViewById<TextView>(R.id.loginText)
        val next = findViewById<Button>(R.id.nextButton)
        val nameInput = findViewById<EditText>(R.id.nameInput)
        val contactInput = findViewById<EditText>(R.id.contactInput)
        val addressInput = findViewById<EditText>(R.id.addressInput)

        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        next.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val contact = contactInput.text.toString().trim()
            val address = addressInput.text.toString().trim()

            if (name.isEmpty() || contact.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, SignUpPage2::class.java).apply {
                    putExtra("name", name)
                    putExtra("contact", contact)
                    putExtra("address", address)
                }
                startActivity(intent)
            }
        }
    }
}
