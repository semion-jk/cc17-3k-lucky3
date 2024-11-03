package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OrderConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_confirmation_screen)

        // Retrieve data from the Intent
        val serviceType = intent.getStringExtra("SERVICE_TYPE") ?: "Not specified"
        val timeDate = intent.getStringExtra("TIME_DATE") ?: "Not specified"
        val laundryPreferences = intent.getStringExtra("LAUNDRY_PREFERENCES") ?: "Not specified"
        val pickupLocation = intent.getStringExtra("PICKUP_LOCATION") ?: "Not specified"

        // Find TextViews and set the retrieved data
        val serviceTypeValue = findViewById<TextView>(R.id.service_type_value)
        val timeDateValue = findViewById<TextView>(R.id.time_date_value)
        val laundryPreferencesValue = findViewById<TextView>(R.id.laundry_preferences_value)
        val pickupLocationValue = findViewById<TextView>(R.id.pickup_location_value)

        serviceTypeValue.text = serviceType
        timeDateValue.text = timeDate
        laundryPreferencesValue.text = laundryPreferences
        pickupLocationValue.text = pickupLocation

        // Set up the Back button
        val backOrderButton = findViewById<Button>(R.id.back_button)
        backOrderButton.setOnClickListener {
            val intent = Intent(this, OrderScreenActivity::class.java)
            startActivity(intent)
        }

        // Set up the Confirm button
        val confirmButton = findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            // Send back confirmation to AppHomeActivity
            val intent = Intent(this, AppHomeActivity::class.java)
            intent.putExtra("ORDER_PLACED", true) // Add an extra indicating order is placed
            startActivity(intent)
        }
    }
}
