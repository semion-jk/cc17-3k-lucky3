package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
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
        val orderNavigationButton = findViewById<TextView>(R.id.buttonText)
        val menu = findViewById<ImageView>(R.id.menuIcon)

        // Navigate to OrderScreenActivity when "Place New Order" button is clicked
        orderNavigationButton.setOnClickListener {
            val intent = Intent(this, OrderScreenActivity::class.java)
            startActivity(intent)
        }
        menu.setOnClickListener {
            val intent = Intent(this, ProfileMenuActivity::class.java)
            startActivity(intent)
        }

        // Check if ORDER_CONFIRMED flag is passed
        val orderPlaced = intent.getBooleanExtra("ORDER_PLACED", false)
        if (orderPlaced) {
            // Show the Active Order card
            displayLastOrder(activeOrderCard)
        } else {
            // Hide the Active Order card by default
            activeOrderCard.visibility = View.GONE
        }
    }

    private fun displayLastOrder(card: LinearLayout) {
        val sharedPreferences = getSharedPreferences("ActiveOrders", MODE_PRIVATE)
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
