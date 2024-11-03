package com.example.labadash_application

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.labadash_application.databinding.AccountPageScreenBinding

class AccountPageActivity : AppCompatActivity() {

    private lateinit var binding: AccountPageScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AccountPageScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserProfile()

        binding.backButton.setOnClickListener {
            val intent = Intent(this, ProfileMenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadUserProfile() {
        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "No Name")
        val contact = sharedPreferences.getString("contact", "No Contact")
        val address = sharedPreferences.getString("address", "No Address")

        binding.nameInput.text = name
        binding.phoneInput.text = "$contact"
        binding.addressInput.text = "$address"
    }
}
