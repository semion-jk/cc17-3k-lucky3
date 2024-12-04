package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class AppHomeActivity : AppCompatActivity() {

    private lateinit var activeOrderCard: LinearLayout
    private val handler = Handler(Looper.getMainLooper())
    private var orderStatusRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_home_page)

        activeOrderCard = findViewById(R.id.active_order_card)
        val orderNavigationButton = findViewById<TextView>(R.id.buttonText)
        val menu = findViewById<ImageView>(R.id.menuIcon)

        // By default, hide the active order card
        activeOrderCard.visibility = View.GONE

        // Navigate to OrderScreenActivity when "Place New Order" button is clicked
        orderNavigationButton.setOnClickListener {
            val intent = Intent(this, OrderScreenActivity::class.java)
            startActivity(intent)
        }

        // Navigate to ProfileMenuActivity when the menu icon is clicked
        menu.setOnClickListener {
            val intent = Intent(this, ProfileMenuActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh the active order card when the activity resumes
        if (isOrderSubmitted()) {
            displayLastOrder()
            startStatusUpdateCycle()
        } else {
            activeOrderCard.visibility = View.GONE
        }
    }

    override fun onPause() {
        super.onPause()
        // Stop status update cycle when activity is not visible
        stopStatusUpdateCycle()
    }

    private fun saveOrderData(serviceType: String, status: String) {
        val sharedPreferences = getSharedPreferences("ActiveOrders", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Create a JSON object for the order
        val orderData = JSONObject().apply {
            put("serviceType", serviceType)
            put("status", status)
        }

        // Save the JSON object as a string in SharedPreferences
        editor.putString("last_order", orderData.toString())
        editor.putBoolean("order_submitted", true) // Mark that an order has been submitted
        editor.apply()
    }

    private fun displayLastOrder() {
        val sharedPreferences = getSharedPreferences("ActiveOrders", MODE_PRIVATE)
        val lastOrderJson = sharedPreferences.getString("last_order", null)

        if (!lastOrderJson.isNullOrEmpty()) {
            // Parse the saved JSON and populate the order card
            val lastOrder = JSONObject(lastOrderJson)
            val orderTitle = activeOrderCard.findViewById<TextView>(R.id.active_order_title)
            val orderItem = activeOrderCard.findViewById<TextView>(R.id.order_item)
            val orderStatus = activeOrderCard.findViewById<TextView>(R.id.order_status)

            orderTitle.text = "Active Order"
            orderItem.text = "Service Type: ${lastOrder.getString("serviceType")}"
            orderStatus.text = "Status: ${lastOrder.getString("status")}"

            // Make the card visible
            activeOrderCard.visibility = View.VISIBLE
        } else {
            // Hide the card if no active order exists
            activeOrderCard.visibility = View.GONE
        }
    }

    private fun startStatusUpdateCycle() {
        // Check if the order has been initialized correctly and has the right status
        val sharedPreferences = getSharedPreferences("ActiveOrders", MODE_PRIVATE)
        val lastOrderJson = sharedPreferences.getString("last_order", null)

        if (lastOrderJson.isNullOrEmpty()) {
            // If no order data exists, set the initial status as "Pending"
            val initialOrder = JSONObject().apply {
                put("serviceType", "Example Service")  // Set your service type here
                put("status", "Pending")  // Initial status is "Pending"
            }
            saveOrderData("Example Service", "Pending")
        }

        var statusUpdateCount = 0 // Keeps track of status changes
        orderStatusRunnable = object : Runnable {
            override fun run() {
                val lastOrderJson = sharedPreferences.getString("last_order", null)

                if (!lastOrderJson.isNullOrEmpty()) {
                    val lastOrder = JSONObject(lastOrderJson)
                    val currentStatus = lastOrder.getString("status")

                    // Log the current status
                    Log.d("OrderStatus", "Current status: $currentStatus")

                    // Determine the next status
                    val nextStatus = when (currentStatus) {
                        "Pending" -> "Ongoing"
                        "Ongoing" -> "Ready to Pickup"
                        else -> null // No further updates if status is already "Ready to Pickup"
                    }

                    // Update status if applicable
                    if (nextStatus != null) {
                        lastOrder.put("status", nextStatus)
                        val editor = sharedPreferences.edit()
                        editor.putString("last_order", lastOrder.toString())
                        editor.apply()

                        // Log the updated status
                        Log.d("OrderStatus", "Updated status: $nextStatus")

                        // Refresh UI
                        displayLastOrder()

                        // Increment update count
                        statusUpdateCount++
                    }

                    // Schedule the next update (stop if 2 updates are done)
                    if (statusUpdateCount < 2) {
                        handler.postDelayed(this, 30000) // 30 seconds delay
                    } else if (statusUpdateCount == 2) {
                        // After reaching "Ready to Pickup", hide the order card after 1 minute
                        handler.postDelayed({
                            activeOrderCard.visibility = View.GONE
                        }, 30000) // 1 minute delay
                    }
                }
            }
        }

        // Start the cycle
        handler.post(orderStatusRunnable!!)
    }

    private fun stopStatusUpdateCycle() {
        orderStatusRunnable?.let { handler.removeCallbacks(it) }
    }

    // Check if an order has been submitted
    private fun isOrderSubmitted(): Boolean {
        val sharedPreferences = getSharedPreferences("ActiveOrders", MODE_PRIVATE)
        return sharedPreferences.getBoolean("order_submitted", false)
    }

    // Disable back button press
    override fun onBackPressed() {
        // Prevent back navigation
    }
}
