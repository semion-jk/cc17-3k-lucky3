// AppHomeActivity.kt
package com.example.labadash_application

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class AppHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_home_page)

        // Find the active order card layout
        val activeOrderCard = findViewById<LinearLayout>(R.id.active_order_card)

        // Check if ORDER_CONFIRMED flag is passed
        val orderConfirmed = intent.getBooleanExtra("ORDER_CONFIRMED", false)
        if (orderConfirmed) {
            // Show the Active Order card with the latest order details
            displayLastOrder(activeOrderCard)
        } else {
            // Hide the Active Order card by default
            activeOrderCard.visibility = View.GONE
        }

        // Navigation button to OrderScreenActivity
        val navigateOrderScreenButton = findViewById<TextView>(R.id.buttonText)
        navigateOrderScreenButton.setOnClickListener {
            val intent = Intent(this, OrderScreenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayLastOrder(card: LinearLayout) {
        val sharedPreferences = getSharedPreferences("ActiveOrders", Context.MODE_PRIVATE)
        val lastOrderJson = sharedPreferences.getString("last_order", null)

        if (lastOrderJson != null) {
            // Parse last order JSON and update card details
            val lastOrder = JSONObject(lastOrderJson)
            val orderTitle = card.findViewById<TextView>(R.id.active_order_title)
            val orderItem = card.findViewById<TextView>(R.id.order_item)
            val orderStatus = card.findViewById<TextView>(R.id.order_status)

            orderTitle.text = "Active Order"
            orderItem.text = "Service Type: ${lastOrder.getString("serviceType")}"
            orderStatus.text = "Status: Pending"

            // Show the card only if there's a confirmed order
            card.visibility = View.VISIBLE
        } else {
            // Hide the card if no order details are saved
            card.visibility = View.GONE
        }
    }
}
