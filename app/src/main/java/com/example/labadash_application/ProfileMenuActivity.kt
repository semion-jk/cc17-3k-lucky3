package com.example.labadash_application

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.labadash_application.databinding.ProfileMenuScreenBinding

class ProfileMenuActivity : AppCompatActivity() {

    private lateinit var binding: ProfileMenuScreenBinding
    private lateinit var dbHelper: DataBaseHelper
    private lateinit var logoutButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileMenuScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DataBaseHelper(this)
        logoutButton = binding.logout // Assuming logout button has this ID

        // Set touch listener to detect clicks outside the button
        val rootLayout = findViewById<View>(R.id.root_layout) // Replace with your root layout ID
        rootLayout.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (!isClickOnButton(event)) {
                    navigateToHome()
                }
            }
            true
        }

        loadUserProfile()

        binding.account.setOnClickListener {
            startActivity(Intent(this, AccountPageActivity::class.java))
        }

        binding.editProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        binding.logout.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        binding.deleteAccount.setOnClickListener {
            showDeleteConfirmationDialog()
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
        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)

        if (username != null) {
            // Delete user from the database using username
            if (dbHelper.deleteUser(username)) {
                clearSharedPreferences()
                Toast.makeText(this, "Account Deleted Successfully", Toast.LENGTH_SHORT).show()
                restartApp()
            } else {
                Toast.makeText(this, "Error deleting account", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No account to delete", Toast.LENGTH_SHORT).show()
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

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Logout") { _, _ ->
                clearSharedPreferences()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun isClickOnButton(event: MotionEvent): Boolean {
        val rect = android.graphics.Rect()
        logoutButton.getHitRect(rect)
        return rect.contains(event.x.toInt(), event.y.toInt())
    }

    private fun navigateToHome() {
        val intent = Intent(this, AppHomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)  // Ensures no back stack
        startActivity(intent)
        finish()  // Finish the current activity so the user cannot go back to it
    }

    override fun onBackPressed() {
        // Override back button to ensure user goes directly to home screen
        navigateToHome()
    }
}
