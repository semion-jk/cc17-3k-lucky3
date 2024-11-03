package com.example.labadash_application

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.labadash_application.databinding.ActivityHomeBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var dataBaseHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataBaseHelper = DataBaseHelper(this)

        binding.loginDoneButton.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            loginUser(username, password)
        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginUser(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            return
        }
        val user = dataBaseHelper.getUser(username, password)
        if (user != null) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

            val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putString("name", user.name)
                putString("contact", user.contact)
                putString("address", user.address)
                apply()
            }

            val intent = Intent(this, AppHomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
        }
    }
}
