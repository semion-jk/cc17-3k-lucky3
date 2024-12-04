package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject

class OrderScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_page_screen)

        val home = findViewById<ImageView>(R.id.home_button)

        home.setOnClickListener {
            val intent = Intent(this, AppHomeActivity::class.java)
            startActivity(intent)
        }

        // Find the spinners and EditText fields
        val serviceTypeSpinner = findViewById<Spinner>(R.id.service_type_spinner)
        val timeDateInput = findViewById<EditText>(R.id.time_date_spinner) // Updated to EditText
        val laundryPreferencesSpinner = findViewById<Spinner>(R.id.laundry_preferences_spinner)
        val pickupLocationInput = findViewById<EditText>(R.id.pickup_delivery_spinner) // Updated to EditText

        // Populate the service type spinner
        val serviceTypes = arrayOf("Choose","Dry Cleaning", "Wash & Fold", "Pressing")
        val serviceTypeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, serviceTypes)
        serviceTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        serviceTypeSpinner.adapter = serviceTypeAdapter

        // Populate the laundry preferences spinner
        val laundryPreferences = arrayOf("Choose","Eco-friendly", "Hypoallergenic", "Scented")
        val laundryPreferencesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, laundryPreferences)
        laundryPreferencesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        laundryPreferencesSpinner.adapter = laundryPreferencesAdapter

        // Confirm Order Button
        val confirmOrderButton = findViewById<Button>(R.id.next_button)
        confirmOrderButton.setOnClickListener {
            // Retrieve the selected or entered details
            val serviceType = serviceTypeSpinner.selectedItem.toString()
            val timeDate = timeDateInput.text.toString()
            val laundryPreferences = laundryPreferencesSpinner.selectedItem.toString()
            val pickupLocation = pickupLocationInput.text.toString()

            // Create an order JSON object to store the order data
            val newOrder = JSONObject()
            newOrder.put("serviceType", serviceType)
            newOrder.put("timeDate", timeDate)
            newOrder.put("laundryPreferences", laundryPreferences)
            newOrder.put("pickupLocation", pickupLocation)

            // Save the new order in SharedPreferences
            saveOrderData(newOrder)

            // Create an Intent to start OrderConfirmationActivity
            val intent = Intent(this, OrderConfirmationActivity::class.java)
            // Pass data to OrderConfirmationActivity (optional)
            intent.putExtra("SERVICE_TYPE", serviceType)
            intent.putExtra("TIME_DATE", timeDate)
            intent.putExtra("LAUNDRY_PREFERENCES", laundryPreferences)
            intent.putExtra("PICKUP_LOCATION", pickupLocation)

            // Start OrderConfirmationActivity
            startActivity(intent)
        }
    }

    private fun saveOrderData(newOrder: JSONObject) {
        val sharedPreferences = getSharedPreferences("ActiveOrders", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Retrieve existing orders
        val existingOrdersJson = sharedPreferences.getString("orders", "[]")
        val ordersArray = JSONArray(existingOrdersJson)

        // Add the new order to the list of orders
        ordersArray.put(newOrder)

        // Save the updated list of orders back to SharedPreferences
        editor.putString("orders", ordersArray.toString())
        editor.apply()
    }
}
