package com.example.labadash_application

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_menu_screen)

        val logout = findViewById<TextView>(R.id.logout)

        logout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val editprofile = findViewById<TextView>(R.id.edit_profile)

        editprofile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
        val account = findViewById<TextView>(R.id.account)

        account.setOnClickListener {
            val intent = Intent(this, AccountPageActivity::class.java)
            startActivity(intent)
        }

        val contentFrame = findViewById<FrameLayout>(R.id.content_frame)

        contentFrame.setOnClickListener {
            val intent = Intent(this, AppHomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.left2, R.anim.right2)
        }
    }
}