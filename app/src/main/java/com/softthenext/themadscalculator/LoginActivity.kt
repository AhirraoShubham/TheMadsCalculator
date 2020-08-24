package com.softthenext.themadscalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    //EditText
    private var etUserLoginID: TextInputEditText? = null
    private var etUserLoginPassword: TextInputEditText? = null

    //Hard-Coded Credentials
    private val mUSERID = "admin@gmail.com"
    private val mPASSWORD = "admin123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //Call Initialization method
        initialization()
    }

    //Initialization method
    private fun initialization() {
        //Credentials
        etUserLoginID = findViewById(R.id.etUserLoginID)
        etUserLoginPassword = findViewById(R.id.etUserLoginPassword)
        //Login
        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        btnSignIn.setOnClickListener {
            val mUserId = etUserLoginID!!.text.toString()
            val mPassword = etUserLoginPassword!!.text.toString()

            when {
                mUserId.isEmpty() -> {
                    Toast.makeText(this, "Enter User ID", Toast.LENGTH_LONG).show()
                }
                mPassword.isEmpty() -> {
                    Toast.makeText(this, "Enter Password", Toast.LENGTH_LONG).show()
                }
                mUserId.equals(
                    mUSERID,
                    ignoreCase = true
                ) && mPassword.equals(
                    mPASSWORD,
                    ignoreCase = true) -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
