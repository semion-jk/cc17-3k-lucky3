package com.example.labadash_application

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.labadash_application.databinding.ProfileMenuScreenBinding

class ProfileMenuActivity : AppCompatActivity() {

    private lateinit var binding: ProfileMenuScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileMenuScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserProfile()

        binding.account.setOnClickListener {
            startActivity(Intent(this, AccountPageActivity::class.java))
        }

        binding.editProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
        binding.logout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun loadUserProfile() {
        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "No Name")
        val contact = sharedPreferences.getString("contact", "No Contact")
        val address = sharedPreferences.getString("address", "No Address")

        binding.userName.text = name
        binding.userContact.text = "Contact: $contact"
        binding.userAddress.text = "Address: $address"
    }

    override fun onResume() {
        super.onResume()
        loadUserProfile()
    }
}
