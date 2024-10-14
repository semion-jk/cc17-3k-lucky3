package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OrderScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_page_screen)

        val homebutton = findViewById<ImageView>(R.id.home_button)

        homebutton.setOnClickListener {
            val intent = Intent(this, AppHomeActivity::class.java)
            startActivity(intent)
        }
        val tracking = findViewById<ImageView>(R.id.map_button)

        tracking.setOnClickListener {
            val intent = Intent(this, TrackingActivity::class.java)
            startActivity(intent)
        }
        val confirmation = findViewById<Button>(R.id.next_button)

        confirmation.setOnClickListener {
            val intent = Intent(this, OrderConfirmationActivity::class.java)
            startActivity(intent)
        }

        setupSpinner(R.id.service_type_spinner, R.array.service_types)
        setupSpinner(R.id.laundry_preferences_spinner, R.array.laundry_preferences)
        setupSpinner(R.id.time_date_spinner, R.array.time_date)
        setupSpinner(R.id.pickup_delivery_spinner, R.array.pickup_delivery)
    }
    private fun setupSpinner(spinnerId: Int, arrayId: Int) {
        val spinner: Spinner = findViewById(spinnerId)
        ArrayAdapter.createFromResource(
            this,
            arrayId,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

    }
}