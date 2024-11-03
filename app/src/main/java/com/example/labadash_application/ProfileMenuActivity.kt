package com.example.labadash_application

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.labadash_application.databinding.ProfileMenuScreenBinding

class ProfileMenuActivity : AppCompatActivity() {

    private lateinit var binding: ProfileMenuScreenBinding
    private lateinit var dbHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileMenuScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DataBaseHelper(this)

        loadUserProfile()

        binding.deleteAccount.setOnClickListener {
            showDeleteConfirmationDialog()
        }

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

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                deleteAccountData()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteAccountData() {
        if (dbHelper.deleteAllUsers()) {
            clearSharedPreferences()
            restartApp()
        }
    }

    private fun clearSharedPreferences() {
        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    private fun restartApp() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        loadUserProfile()
    }
}
