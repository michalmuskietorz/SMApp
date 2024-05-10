package com.example.lsmapp


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DatabaseHelper(this)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Check if the user already exists
                val existingUser = dbHelper.getUserByEmail(email)
                if (existingUser == null) {
                    // Create a new user and insert into the database
                    val newUser = User(user_id = 0, username = username, email = email)
                    dbHelper.insertUser(newUser)
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Check if the user exists and the password is correct
                val user = dbHelper.getUserByEmail(email)
                if (user != null) {
                    // Password checking could be added here if you store password hashes in the database
                    // For simplicity, skipping password verification in this example
                    // Login successful, navigate to the main activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
