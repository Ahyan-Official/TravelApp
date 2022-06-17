package com.travelingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.GsonBuilder
import com.travelingapp.model.User
import com.travelingapp.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class QuizActivity : AppCompatActivity() {

    private var txtQuestion: TextView? = null
    private var txtScore: TextView? = null
    private var txtQuestionCount: TextView? = null
    private var radioGroup: RadioGroup? = null
    private var r1: RadioButton? = null
    private var r2: RadioButton? = null
    private var r3: RadioButton? = null
    private var r4: RadioButton? = null

    private var mSubmit: Button? = null
    private lateinit var auth: FirebaseAuth

    var id = 0;var qNo = 0;
    var totalQuestions = 0;
    var total = 0
    var ioipo = 0;
    var score = 0;
    var idStr = "0"
    var itemsArray: ArrayList<Cell> = ArrayList()
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var database: DatabaseReference
    private lateinit var databaseUsers: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)


        idStr = intent.getStringExtra("id").toString()
        id = idStr.toInt()

        Log.e("poooo", "onCreate: "+id )

        txtQuestion = findViewById(R.id.Question)
        txtScore = findViewById(R.id.Score)
        txtQuestionCount = findViewById(R.id.questionCount)
        radioGroup = findViewById(R.id.radioGroup)
        r1 = findViewById(R.id.radioButton1)
        r2 = findViewById(R.id.radioButton2)
        r3 = findViewById(R.id.radioButton3)
        r4 = findViewById(R.id.radioButton4)

        mSubmit = findViewById(R.id.submitButton)

        database = FirebaseDatabase.getInstance().reference
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        setupQuestionAndAnswer(id.toString(),0)

        var arrayAns= mutableListOf("Secret ballots","Ayers Rock","Taking a helicopter tour","Treason","Bilby")


        var arrayAnsCountryID1= mutableListOf("Secret ballots","The Great Barrier Reef","Swimming in the ocean","Petty theft","Bilby")
        var arrayAnsCountryID2= mutableListOf("Buenos Aires","5","Aconcagua","Italy")
        var arrayAnsCountryID3= mutableListOf("Liège","Hastings","Democratic Republic of the Congo","Schelde")
        var arrayAnsCountryID4= mutableListOf("Renminbi","East Asia","3rd","People's Republic of China","One")
        var arrayAnsCountryID5= mutableListOf("Mexico City","Peso","31","1846 to 1848","Four")
        var arrayAnsCountryID6= mutableListOf("3,754 metres","1947","Kiwi","1892","Reptile")

        mSubmit!!.setOnClickListener{


            if(id==1){

                if(qNo<totalQuestions){

                    if (r1!!.isChecked || r2!!.isChecked || r3!!.isChecked|| r4!!.isChecked) {

                    if(r1!!.isChecked && r1!!.text.toString() ==arrayAnsCountryID1[qNo]){
                        score+=1
                        Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                    }else if(r2!!.isChecked &&r2!!.text.toString() ==arrayAnsCountryID1[qNo]){
                        score+=1
                        Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                    }else if(r3!!.isChecked &&r3!!.text.toString() == arrayAnsCountryID1[qNo]){
                        score+=1
                        Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                    }else if(r4!!.isChecked &&r4!!.text.toString() ==arrayAnsCountryID1[qNo]){
                        score+=1

                        Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                    }


                    txtScore?.text  = "Score $score"

                    //Show correct answer
                    if(r1!!.text.toString() ==arrayAnsCountryID1[qNo]){
                        Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID1[qNo] , Toast.LENGTH_SHORT).show()

                    }else if(r2!!.text.toString() ==arrayAnsCountryID1[qNo]){

                        Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID1[qNo] , Toast.LENGTH_SHORT).show()

                    }else if(r3!!.text.toString() == arrayAnsCountryID1[qNo]){

                        Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID1[qNo] , Toast.LENGTH_SHORT).show()

                    }else if(r4!!.text.toString() ==arrayAnsCountryID1[qNo]){

                        Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID1[qNo] , Toast.LENGTH_SHORT).show()

                    }

                    qNo += 1

                    if(qNo<totalQuestions){

                        setupQuestionAndAnswer(id.toString(),qNo)


                    }else{
                        //go to result and save score

                        insertDataToDatabase()


                    }



                } else {
                        Toast.makeText(this@QuizActivity, "No Option selected", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    //go to result and save score

                    insertDataToDatabase()


                }


            }else if(id==2){

                if(qNo<totalQuestions){
                    if (r1!!.isChecked || r2!!.isChecked || r3!!.isChecked|| r4!!.isChecked) {

                        if(r1!!.isChecked && r1!!.text.toString() ==arrayAnsCountryID2[qNo]){
                            score+=1
                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }else if(r2!!.isChecked &&r2!!.text.toString() ==arrayAnsCountryID2[qNo]){
                            score+=1
                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }else if(r3!!.isChecked &&r3!!.text.toString() == arrayAnsCountryID2[qNo]){
                            score+=1
                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }else if(r4!!.isChecked &&r4!!.text.toString() ==arrayAnsCountryID2[qNo]){
                            score+=1

                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }


                        txtScore?.text  = "Score $score"

                        //Show correct answer
                        if(r1!!.text.toString() ==arrayAnsCountryID2[qNo]){
                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID2[qNo] , Toast.LENGTH_SHORT).show()

                        }else if(r2!!.text.toString() ==arrayAnsCountryID2[qNo]){

                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID2[qNo] , Toast.LENGTH_SHORT).show()

                        }else if(r3!!.text.toString() == arrayAnsCountryID2[qNo]){

                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID2[qNo] , Toast.LENGTH_SHORT).show()

                        }else if(r4!!.text.toString() ==arrayAnsCountryID2[qNo]){

                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID2[qNo] , Toast.LENGTH_SHORT).show()

                        }

                        qNo += 1

                        if(qNo<totalQuestions){

                            setupQuestionAndAnswer(id.toString(),qNo)


                        }else{
                            //go to result and save score

                            insertDataToDatabase()


                        }



                    } else {
                        Toast.makeText(this@QuizActivity, "No Option selected", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    //go to result and save score

                    insertDataToDatabase()


                }


            }else if(id==3){

                if(qNo<totalQuestions){
                    if (r1!!.isChecked || r2!!.isChecked || r3!!.isChecked|| r4!!.isChecked) {

                        if(r1!!.isChecked && r1!!.text.toString() ==arrayAnsCountryID3[qNo]){
                            score+=1
                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }else if(r2!!.isChecked &&r2!!.text.toString() ==arrayAnsCountryID3[qNo]){
                            score+=1
                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }else if(r3!!.isChecked &&r3!!.text.toString() == arrayAnsCountryID3[qNo]){
                            score+=1
                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }else if(r4!!.isChecked &&r4!!.text.toString() ==arrayAnsCountryID3[qNo]){
                            score+=1

                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }


                        txtScore?.text  = "Score $score"

                        //Show correct answer
                        if(r1!!.text.toString() ==arrayAnsCountryID3[qNo]){
                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID3[qNo] , Toast.LENGTH_SHORT).show()

                        }else if(r2!!.text.toString() ==arrayAnsCountryID3[qNo]){

                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID3[qNo] , Toast.LENGTH_SHORT).show()

                        }else if(r3!!.text.toString() == arrayAnsCountryID3[qNo]){

                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID3[qNo] , Toast.LENGTH_SHORT).show()

                        }else if(r4!!.text.toString() ==arrayAnsCountryID3[qNo]){

                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID3[qNo] , Toast.LENGTH_SHORT).show()

                        }

                        qNo += 1

                        if(qNo<totalQuestions){

                            setupQuestionAndAnswer(id.toString(),qNo)


                        }else{
                            //go to result and save score

                            insertDataToDatabase()


                        }



                    } else {
                        Toast.makeText(this@QuizActivity, "No Option selected", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    //go to result and save score

                    insertDataToDatabase()


                }



            }else if(id==4){

                if(qNo<totalQuestions){
                    if (r1!!.isChecked || r2!!.isChecked || r3!!.isChecked|| r4!!.isChecked) {

                        if(r1!!.isChecked && r1!!.text.toString() ==arrayAnsCountryID4[qNo]){
                            score+=1
                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }else if(r2!!.isChecked &&r2!!.text.toString() ==arrayAnsCountryID4[qNo]){
                            score+=1
                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }else if(r3!!.isChecked &&r3!!.text.toString() == arrayAnsCountryID4[qNo]){
                            score+=1
                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }else if(r4!!.isChecked &&r4!!.text.toString() ==arrayAnsCountryID4[qNo]){
                            score+=1

                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }


                        txtScore?.text  = "Score $score"

                        //Show correct answer
                        if(r1!!.text.toString() ==arrayAnsCountryID4[qNo]){
                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID4[qNo] , Toast.LENGTH_SHORT).show()

                        }else if(r2!!.text.toString() ==arrayAnsCountryID4[qNo]){

                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID4[qNo] , Toast.LENGTH_SHORT).show()

                        }else if(r3!!.text.toString() == arrayAnsCountryID4[qNo]){

                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID4[qNo] , Toast.LENGTH_SHORT).show()

                        }else if(r4!!.text.toString() ==arrayAnsCountryID4[qNo]){

                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID4[qNo] , Toast.LENGTH_SHORT).show()

                        }

                        qNo += 1

                        if(qNo<totalQuestions){

                            setupQuestionAndAnswer(id.toString(),qNo)


                        }else{
                            //go to result and save score

                            insertDataToDatabase()


                        }



                    } else {
                        Toast.makeText(this@QuizActivity, "No Option selected", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    //go to result and save score

                    insertDataToDatabase()


                }

            }else if(id==5){

                if(qNo<totalQuestions){
                    if (r1!!.isChecked || r2!!.isChecked || r3!!.isChecked|| r4!!.isChecked) {

                        if(r1!!.isChecked && r1!!.text.toString() ==arrayAnsCountryID5[qNo]){
                            score+=1
                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }else if(r2!!.isChecked &&r2!!.text.toString() ==arrayAnsCountryID5[qNo]){
                            score+=1
                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }else if(r3!!.isChecked &&r3!!.text.toString() == arrayAnsCountryID5[qNo]){
                            score+=1
                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }else if(r4!!.isChecked &&r4!!.text.toString() ==arrayAnsCountryID5[qNo]){
                            score+=1

                            Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                        }


                        txtScore?.text  = "Score $score"

                        //Show correct answer
                        if(r1!!.text.toString() ==arrayAnsCountryID5[qNo]){
                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID5[qNo] , Toast.LENGTH_SHORT).show()

                        }else if(r2!!.text.toString() ==arrayAnsCountryID5[qNo]){

                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID5[qNo] , Toast.LENGTH_SHORT).show()

                        }else if(r3!!.text.toString() == arrayAnsCountryID5[qNo]){

                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID5[qNo] , Toast.LENGTH_SHORT).show()

                        }else if(r4!!.text.toString() ==arrayAnsCountryID5[qNo]){

                            Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID5[qNo] , Toast.LENGTH_SHORT).show()

                        }

                        qNo += 1

                        if(qNo<totalQuestions){

                            setupQuestionAndAnswer(id.toString(),qNo)


                        }else{
                            //go to result and save score

                            insertDataToDatabase()


                        }



                    } else {
                        Toast.makeText(this@QuizActivity, "No Option selected", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    //go to result and save score

                    insertDataToDatabase()


                }

            }else if(id==6){

                if(qNo<totalQuestions){

                    if (r1!!.isChecked || r2!!.isChecked || r3!!.isChecked|| r4!!.isChecked) {

                    if(r1!!.isChecked && r1!!.text.toString() ==arrayAnsCountryID6[qNo]){
                        score+=1
                        Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                    }else if(r2!!.isChecked &&r2!!.text.toString() ==arrayAnsCountryID6[qNo]){
                        score+=1
                        Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                    }else if(r3!!.isChecked &&r3!!.text.toString() == arrayAnsCountryID6[qNo]){
                        score+=1
                        Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                    }else if(r4!!.isChecked &&r4!!.text.toString() ==arrayAnsCountryID6[qNo]){
                        score+=1

                        Toast.makeText(this@QuizActivity, "Correct", Toast.LENGTH_SHORT).show()

                    }


                    txtScore?.text  = "Score $score"

                    //Show correct answer
                    if(r1!!.text.toString() ==arrayAnsCountryID6[qNo]){
                        Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID6[qNo] , Toast.LENGTH_SHORT).show()

                    }else if(r2!!.text.toString() ==arrayAnsCountryID6[qNo]){

                        Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID6[qNo] , Toast.LENGTH_SHORT).show()

                    }else if(r3!!.text.toString() == arrayAnsCountryID6[qNo]){

                        Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID6[qNo] , Toast.LENGTH_SHORT).show()

                    }else if(r4!!.text.toString() ==arrayAnsCountryID6[qNo]){

                        Toast.makeText(this@QuizActivity, "Correct Answer is "+arrayAnsCountryID6[qNo] , Toast.LENGTH_SHORT).show()

                    }

                    qNo += 1

                    if(qNo<totalQuestions){

                        setupQuestionAndAnswer(id.toString(),qNo)


                    }else{
                        //go to result and save score

                        insertDataToDatabase()


                    }



                } else {
                        Toast.makeText(this@QuizActivity, "No Option selected", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    //go to result and save score

                    insertDataToDatabase()


                }
            }




            }

    }
    private fun insertDataToDatabase() {

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if(currentUser!=null){
            val checkListern = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){


                        var fname = snapshot.child("firstName").value.toString()
                        var lname = snapshot.child("lastName").value.toString()
                        var email = snapshot.child("email").value.toString()

                        if(fname.isNotEmpty() && lname.isNotEmpty() || email.isNotEmpty() ){

                            // Create User Object
                            val user = User(
                                0,
                                fname,
                                lname,
                                score,
                                currentUser.uid,
                                idStr
                            )
                            // Add Data to Database
                            mUserViewModel.addUser(user)
                            Toast.makeText(this@QuizActivity, "Successfully added!", Toast.LENGTH_LONG).show()
                            // Navigate Back
                            //findNavController().navigate(R.id.action_addFragment_to_listFragment)

                            val intent = Intent(this@QuizActivity, QuizResultsActivity::class.java)
                            intent.putExtra("value", "quiz")
                            startActivity(intent)
                        }

                    }else{



                        Toast.makeText(this@QuizActivity, "Please Login", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }



            if (currentUser != null) {
                databaseUsers= database.child("users").child(currentUser.uid)

                databaseUsers.addValueEventListener(checkListern)
            }

        }




    }


    private fun check(id:Int) {
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
                    Log.d("Pretty Printed JSON :", prettyJson)
                    //binding.jsonResultsTextview.text = prettyJson
                    val items = response.body()


                    val itemsquiz = items?.get(idint-1)?.quiz
                    if (itemsquiz != null) {
                        txtQuestionCount?.text = "Questions "+ itemsquiz.count().toString()


                        var employeeAge2 = itemsquiz[0].question?.toString() ?: "N/A"

                        var option1 = itemsquiz[idint-1].answers?.get(0).toString() ?: "N/A"
                        var option2 = itemsquiz[idint-1].answers?.get(1).toString() ?: "N/A"
                        var option3 = itemsquiz[idint-1].answers?.get(2).toString() ?: "N/A"
                        var option4 = itemsquiz[idint-1].answers?.get(3).toString() ?: "N/A"


                        txtQuestion?.text =employeeAge2
                        r1?.text = option1
                        r2?.text = option2
                        r3?.text = option3
                        r4?.text = option4




                    }


                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }

    private fun setupQuestionAndAnswer(id:String,qNo:Int) {
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
                    val itemsquiz = country?.get(idint-1)?.quiz//country will same

                    if (itemsquiz != null) {
                        totalQuestions = itemsquiz.count()


                        Log.e("rrr", "asdsa " + id + "sad " + totalQuestions);
                        Log.e("rrr :", itemsquiz.toString())

                        if (itemsquiz != null) {
                            //Log.e("eee", "setupQuestionAndAnswer: "+ itemsquiz[0].answers?.get(0))
                            txtQuestionCount?.text = "Total Questions " + itemsquiz.count().toString()


                            val employeeAge2 = itemsquiz[qNo ].question?.toString() ?: "N/A"

                            val option1 = itemsquiz[qNo].answers?.get(0).toString() ?: "N/A"
                            val option2 = itemsquiz[qNo].answers?.get(1).toString() ?: "N/A"
                            val option3 = itemsquiz[qNo].answers?.get(2).toString() ?: "N/A"
                            val option4 = itemsquiz[qNo].answers?.get(3).toString() ?: "N/A"


                            txtQuestion?.text = employeeAge2
                            r1?.text = option1
                            r2?.text = option2
                            r3?.text = option3
                            r4?.text = option4


                        }

                    }

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }



//    private fun setupQuestionAndAnswer() {
//
//
//        // Create Retrofit
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://raw.githubusercontent.com")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        // Create Service
//        val service = retrofit.create(APIServiceData::class.java)
//        CoroutineScope(Dispatchers.IO).launch {
//
//            // Do the GET request and get response
//            val response = service.getEmployees()
//
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//
//                    // Convert raw JSON to pretty JSON using GSON library
//                    val gson = GsonBuilder().setPrettyPrinting().create()
//                    val prettyJson = gson.toJson(response.body())
//                    Log.d("Pretty Printed JSON :", prettyJson)
//
//                    val items = response.body()
//                    if (items != null) {
//                        for (i in 0 until items.count()) {
//
//
//
//                            var employeeAge = "asd"
//
//
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
//
//
//                            val model = Cell(id,name, continent, langCode, flagImg)
//                            itemsArray.add(model)
//
//                            adapter = RVAdapter(itemsArray)
//                            adapter.notifyDataSetChanged()
//
//                        }
//
//                    }
//
//                    // Pass the Array with data to RecyclerView Adapter
//                    binding.jsonResultsRecyclerview.adapter = adapter
//
//
//                } else {
//
//                    Log.e("RETROFIT_ERROR", response.code().toString())
//
//                }
//            }
//        }
//    }


}