package com.example.labadash_application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView

class StatusUpdateActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private lateinit var etaTextView: TextView
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_screen_activity)

        // Initialize views
        statusTextView = findViewById(R.id.statusTextView)
        etaTextView = findViewById(R.id.etaTextView)
        backButton = findViewById(R.id.backButton)

        // Here you would fetch the status from your data source
        // For demonstration, we'll set static values
        val orderStatus = "Cooking" // Replace with actual status retrieval logic
        val eta = "15 minutes" // Replace with actual ETA retrieval logic

        updateStatus(orderStatus, eta)

        // Back button click listener
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun updateStatus(status: String, eta: String) {
        statusTextView.text = "Current Status: $status"
        etaTextView.text = "Estimated Time: $eta"
    }
}
