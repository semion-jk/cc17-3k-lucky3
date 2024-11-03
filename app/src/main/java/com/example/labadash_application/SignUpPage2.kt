package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.labadash_application.databinding.SignUpPage2Binding

class SignUpPage2 : AppCompatActivity() {

    private lateinit var binding: SignUpPage2Binding
    private lateinit var dataBaseHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpPage2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        dataBaseHelper = DataBaseHelper(this)

        val name = intent.getStringExtra("name")
        val contact = intent.getStringExtra("contact")
        val address = intent.getStringExtra("address")

        binding.signupButton.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            } else {
                if (name != null && contact != null && address != null) {
                    signupDatabase(name, contact, address, username, password)
                } else {
                    Toast.makeText(this, "Error retrieving data", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.redirectlogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signupDatabase(name: String, contact: String, address: String, username: String, password: String) {
        val isAdded = dataBaseHelper.addUser(name,  contact, address, username, password)
        if (isAdded) {
            Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show()
        }
    }
}
