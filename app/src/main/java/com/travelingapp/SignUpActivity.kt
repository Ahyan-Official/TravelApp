package com.travelingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    lateinit var etFirstName: EditText
    lateinit var etLastName: EditText

    lateinit var etConfPass: EditText
    private lateinit var etPass: EditText
    private lateinit var btnSignUp: Button
    lateinit var tvRedirectLogin: LinearLayout

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // View Bindings
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)

        etEmail = findViewById(R.id.etSEmailAddress)
        etConfPass = findViewById(R.id.etSConfPassword)
        etPass = findViewById(R.id.etSPassword)
        btnSignUp = findViewById(R.id.btnSSigned)
        tvRedirectLogin = findViewById(R.id.tvRedirectLogin)
        database = FirebaseDatabase.getInstance().reference


        // Initialising auth object
        auth =FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            signUpUser()
        }

        // switching from signUp Activity to Login Activity
        tvRedirectLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun signUpUser() {
        val first = etFirstName.text.toString()
        val last = etLastName.text.toString()

        val email = etEmail.text.toString()
        val pass = etPass.text.toString()

        val confirmPassword = etConfPass.text.toString()

        //check pass
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }

        // If all credential are correct
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {



                if(auth.currentUser !=null){
                    database.child("users").child(auth.currentUser!!.uid).child("firstName").setValue(first)
                    database.child("users").child(auth.currentUser!!.uid).child("lastName").setValue(last)
                    database.child("users").child(auth.currentUser!!.uid).child("email").setValue(email)
                    Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }




            } else {

                Toast.makeText(this, it.exception?.message  , Toast.LENGTH_SHORT).show()

                //Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()

            }
        }
    }
}