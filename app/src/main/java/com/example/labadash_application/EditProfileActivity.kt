package com.example.labadash_application

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.labadash_application.databinding.ProfilePageScreenBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ProfilePageScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfilePageScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        binding.nameInput.setText(sharedPreferences.getString("name", ""))
        binding.phoneInput.setText(sharedPreferences.getString("contact", ""))
        binding.addressInput.setText(sharedPreferences.getString("address", ""))

        binding.doneButton.setOnClickListener {
            val newName = binding.nameInput.text.toString()
            val newContact = binding.phoneInput.text.toString()
            val newAddress = binding.addressInput.text.toString()

            sharedPreferences.edit().apply {
                putString("name", newName)
                putString("contact", newContact)
                putString("address", newAddress)
                apply()
            }

            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProfileMenuActivity::class.java))
            finish()
        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, ProfileMenuActivity::class.java))
            finish()
        }
    }
}
