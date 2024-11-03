package com.example.labadash_application

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "users.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_CONTACT = "contact"
        private const val COLUMN_ADDRESS = "address"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_CONTACT TEXT, " +
                "$COLUMN_ADDRESS TEXT, " +
                "$COLUMN_USERNAME TEXT UNIQUE, " +
                "$COLUMN_PASSWORD TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // Method to add a new user to the database
    fun addUser(name: String, address: String, contact: String, username: String, password: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_ADDRESS, address)
            put(COLUMN_CONTACT, contact)
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
        }

        val result = db.insert(TABLE_USERS, null, contentValues)
        db.close()
        return result != -1L // returns true if row was inserted successfully
    }
    fun getUser(username: String, password: String): User? {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_NAME, $COLUMN_CONTACT, $COLUMN_ADDRESS FROM $TABLE_USERS WHERE $COLUMN_USERNAME=? AND $COLUMN_PASSWORD=?"
        val cursor = db.rawQuery(query, arrayOf(username, password))

        return try {
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
                val contactIndex = cursor.getColumnIndex(COLUMN_CONTACT)
                val addressIndex = cursor.getColumnIndex(COLUMN_ADDRESS)

                // Check if indexes are valid
                if (nameIndex != -1 && contactIndex != -1 && addressIndex != -1) {
                    val name = cursor.getString(nameIndex)
                    val contact = cursor.getString(contactIndex)
                    val address = cursor.getString(addressIndex)
                    User(name, contact, address) // Return User object
                } else {
                    Log.e("DatabaseError", "One or more column indices are invalid")
                    null
                }
            } else {
                Log.e("DatabaseError", "No user found for the given credentials")
                null // No user found
            }
        } catch (e: Exception) {
            Log.e("DatabaseError", "Error retrieving user: ${e.message}", e)
            null
        } finally {
            cursor.close() // Ensure cursor is closed
            db.close() // Close the database
        }
    }


}
