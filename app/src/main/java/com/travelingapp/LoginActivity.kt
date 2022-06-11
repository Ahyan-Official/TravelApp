package com.travelingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.content.Intent

import android.widget.*

import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var tvRedirectSignUp: LinearLayout
    lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    lateinit var btnLogin: Button
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // View Binding
        tvRedirectSignUp = findViewById(R.id.tvRedirectSignUp)
        btnLogin = findViewById(R.id.btnLogin)
        etEmail = findViewById(R.id.etEmailAddress)
        etPass = findViewById(R.id.etPassword)

        // initialising Firebase auth object
        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            login()
        }

        tvRedirectSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            // using finish() to end the activity
            finish()
        }
    }

    private fun login() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        // calling signInWithEmailAndPassword(email, pass)

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                // using finish() to end the activity
                finish()
            } else
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
        }
    }

}