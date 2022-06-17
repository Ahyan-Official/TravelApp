package com.travelingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_phrase.*
import kotlinx.android.synthetic.main.activity_quiz_results.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhraseActivity : AppCompatActivity() {



    var totalPhrases =0;
    var id = 0;
    var idStr = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phrase)

        idStr = intent.getStringExtra("id").toString()
        id = idStr.toInt()


        home1.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        loadph(idStr);
    }


    private fun loadph(id:String) {
        var idint = id.toInt();


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

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(response.body())
                    //binding.jsonResultsTextview.text = prettyJson
                    val country = response.body()


                    //quiz
                    val phraseList = country?.get(idint-1)?.phrases//phrases will same



                    if (phraseList != null) {
                        totalPhrases = phraseList.count()


                        var pp = ""
                        for(phase in phraseList){

                            ph.text =ph.text.toString()+"\n"+ phase


                        }

                        //ph.text = pp



                    }

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }

}