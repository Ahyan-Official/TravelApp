package com.travelingapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.travelingapp.arrayJSON.CountriesActivity
import com.travelingapp.databinding.ActivityParseBinding


class ParseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)





    }
}