package com.travelingapp.arrayJSON

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.gson.GsonBuilder
import com.travelingapp.APIServiceData
import com.travelingapp.Cell
import com.travelingapp.R
import com.travelingapp.RVAdapter
import com.travelingapp.databinding.ActivityCountriesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class CountriesActivity : AppCompatActivity() {

    var itemsArray: ArrayList<Cell> = ArrayList()
    lateinit var adapter: RVAdapter
    private lateinit var binding: ActivityCountriesBinding

    var taskk = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountriesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Clean TextViews
        binding.jsonResultsTextview.text = ""
        taskk = intent.getStringExtra("value").toString()
        setupRecyclerView()
        parseJSON()

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.jsonResultsRecyclerview.layoutManager = layoutManager
        binding.jsonResultsRecyclerview.setHasFixedSize(true)
        val dividerItemDecoration =
            DividerItemDecoration(binding.jsonResultsRecyclerview.context, layoutManager.orientation)
        ContextCompat.getDrawable(this, R.drawable.line_divider)?.let { drawable ->
            dividerItemDecoration.setDrawable(drawable)
        }
        binding.jsonResultsRecyclerview.addItemDecoration(dividerItemDecoration)
    }

    private fun parseJSON() {

        // .addConverterFactory(GsonConverterFactory.create()) for Gson converter
        // .addConverterFactory(MoshiConverterFactory.create()) for Moshi converter
        // .addConverterFactory(Json.asConverterFactory("application/json".toMediaType())) for Kotlinx Serialization converter
        // .addConverterFactory(JacksonConverterFactory.create()) for Jackson converter

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIServiceData::class.java)
        CoroutineScope(Dispatchers.IO).launch {

            // Do the GET request and get response
            val response = service.getEmployees()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(response.body())
                    Log.d("Pretty Printed JSON :", prettyJson)
                    binding.jsonResultsTextview.text = prettyJson

                    val items = response.body()
                    if (items != null) {
                        for (i in 0 until items.count()) {
                            // ID
                            val id = items[i].id ?: "N/A"
                            Log.d("ID: ", id)

                            // Employee Name
                            val continent = items[i].continent ?: "N/A"
                            Log.d("Employee Name: ", continent)

                            // Employee Salary


                            val name = items[i].name ?: "N/A"
                            Log.d("name: ", name)

                            val langCode = items[i].langCode ?: "N/A"
                            Log.d("langCode: ", langCode)


                            val flagImg = items[i].flagImg ?: "N/A"
                            Log.d("flagImg: ", flagImg)

//                            val employeeSalary = items[i].phrases?.get(0).toString()
//                            Log.d("Employee Salary: ", employeeSalary)
//



                            var employeeSalary = "d"
                            if(items[i].phrases?.isNotEmpty() == true){

                                 employeeSalary = items[i].phrases?.get(0).toString()
                                Log.d("Employee Salary: ", employeeSalary)
                            }
                            // Employee Age
//                            val employeeAge = items[i].langCode ?: "N/A"
//                            Log.d("Employee Age: ", employeeAge)

                            var employeeAge = "asd"
                            val itemsattractions = items[i].attractions
                            if (itemsattractions != null) {

                                for (j in 0 until itemsattractions.count()) {

                                    employeeAge = itemsattractions[i].name?: "N/A"
                                    Log.d("Employee Age: ", employeeAge)

                                }

                            }

//                            val itemsquiz = items[i].quiz
//                            if (itemsquiz != null) {
//
//                                for (j in 0 until itemsquiz.count()) {
//
//                                    employeeAge = itemsquiz[i].answers?.get(0).toString() ?: "N/A"
//                                    Log.d("Employee Age: ", employeeAge)
//
//                                }
//
//                            }


                            val model = Cell(id,name, continent, langCode, flagImg)
                            itemsArray.add(model)

                            adapter = RVAdapter(itemsArray,taskk)
                            adapter.notifyDataSetChanged()

                        }

                    }

                    // Pass the Array with data to RecyclerView Adapter
                    binding.jsonResultsRecyclerview.adapter = adapter


                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
}


