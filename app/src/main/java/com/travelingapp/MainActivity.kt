package com.travelingapp

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.shrikanthravi.library.NightModeButton
import com.travelingapp.activity.MainTranslationActivity
import com.travelingapp.arrayJSON.CountriesActivity
import com.travelingapp.model.User
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var relativeLayout: RelativeLayout? = null
    private lateinit var database: DatabaseReference
    private lateinit var databaseUsers: DatabaseReference

    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        setContentView(R.layout.activity_main)
        database = FirebaseDatabase.getInstance().reference

        val translate = findViewById<CardView>(R.id.translate)
        val speech = findViewById<CardView>(R.id.speech)
        val maps = findViewById<CardView>(R.id.maps)
        val quiz = findViewById<CardView>(R.id.quiz)
        val country = findViewById<CardView>(R.id.country)
        val textView = findViewById<TextView>(R.id.tv)
        val nightModeButton = findViewById<ImageView>(R.id.nightModeButton)

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                val sharedPreference =  getSharedPreferences("THEME", Context.MODE_PRIVATE)
                var editor = sharedPreference.edit()
                editor.putString("theme","dark")
                editor.commit()
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                val sharedPreference =  getSharedPreferences("THEME", Context.MODE_PRIVATE)
                var editor = sharedPreference.edit()
                editor.putString("theme","light")
                editor.commit()
            }
        }
        val sharedPreference =  getSharedPreferences("THEME", Context.MODE_PRIVATE)
        var themeColor = sharedPreference.getString("theme","light")

        if(themeColor =="light"){

            nightModeButton.setImageResource(R.drawable.ic_baseline_light_mode_24)
        }else{
            nightModeButton.setImageResource(R.drawable.ic_baseline_dark_mode_24)

        }

        nightModeButton.setOnClickListener{
            val sharedPreference =  getSharedPreferences("THEME", Context.MODE_PRIVATE)
            var themeColor = sharedPreference.getString("theme","light")

            if(themeColor =="light"){
                nightModeButton.setImageResource(R.drawable.ic_baseline_dark_mode_24)

                val sharedPreference =  getSharedPreferences("THEME", Context.MODE_PRIVATE)
                var editor = sharedPreference.edit()
                editor.putString("theme","dark")
                editor.commit()

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                Toast.makeText(applicationContext, "Night Mode On", Toast.LENGTH_SHORT).show()

            }else{

                nightModeButton.setImageResource(R.drawable.ic_baseline_light_mode_24)

                val sharedPreference =  getSharedPreferences("THEME", Context.MODE_PRIVATE)
                var editor = sharedPreference.edit()
                editor.putString("theme","light")
                editor.commit()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                Toast.makeText(applicationContext, "Night Mode Off", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }

        val navView: NavigationView = findViewById(R.id.nav_view)


        auth = FirebaseAuth.getInstance()
        val currentUser1 = auth.currentUser
        if(currentUser1!=null){
            val checkListern = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){


                        var fname = snapshot.child("firstName").value.toString()
                        var lname = snapshot.child("lastName").value.toString()

                        navView.tv.text = "$fname $lname"

                        Log.e("lpp", "onDataChange: $fname $lname")


                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }



            if (currentUser1 != null) {
                databaseUsers= database.child("users").child(currentUser1.uid)

                databaseUsers.addValueEventListener(checkListern)
            }

        }



        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)


        toggle  = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when(it.itemId){

                R.id.nav_trans -> {
                    val intent = Intent(this,MainTranslationActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_speech -> {
                    val intent = Intent(this,TextToSpeechActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_map -> {

                    val intent = Intent(this,CountriesActivity::class.java)
                    intent.putExtra("value", "map")

                    startActivity(intent)
                }

                R.id.nav_quiz -> {
                    val intent = Intent(this,CountriesActivity::class.java)
                    intent.putExtra("value", "quiz")

                    startActivity(intent)
                }
                R.id.nav_phrase -> {
                    val intent = Intent(this,CountriesActivity::class.java)
                    intent.putExtra("value", "phrase")

                    startActivity(intent)
                }
                R.id.nav_logout ->{

                    auth = FirebaseAuth.getInstance()
                    auth.signOut()
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                }

            }
            true
        }




        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if(currentUser==null){

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)

        }

        translate.setOnClickListener {

            val intent = Intent(this,MainTranslationActivity::class.java)
            startActivity(intent)

        }
        speech.setOnClickListener {

            val intent = Intent(this,TextToSpeechActivity::class.java)
            startActivity(intent)

        }
        maps.setOnClickListener {

            val intent = Intent(this,CountriesActivity::class.java)
            intent.putExtra("value", "map")

            startActivity(intent)

        }
        quiz.setOnClickListener {

            val intent = Intent(this,CountriesActivity::class.java)
            intent.putExtra("value", "quiz")

            startActivity(intent)

        }
        phrase.setOnClickListener {

            val intent = Intent(this,CountriesActivity::class.java)
            intent.putExtra("value", "phrase")

            startActivity(intent)

        }
        country.setOnClickListener {

            val intent = Intent(this,CountriesActivity::class.java)
            intent.putExtra("value", "rrr")
            startActivity(intent)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return true

    }

}