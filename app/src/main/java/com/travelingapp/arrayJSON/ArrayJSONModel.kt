package com.travelingapp.arrayJSON


import com.google.gson.annotations.SerializedName
import java.lang.reflect.Array

// Uncomment the following line if you're using the Kotlinx Serialization converter
// @Serializable
data class ArrayJSONModel(

    // @SerializedName(" ") for the Gson converter
    // @field:Json(name = " ") for the Moshi converter
    // @SerialName(" ") for the Kotlinx Serialization converter
    // @JsonProperty(" ") for the Jackson converter

    @SerializedName("id")
    var id: String?,

    @SerializedName("continent")
    var continent: String?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("langCode")
    var langCode: String?,


    @SerializedName("flagImg")
    var flagImg: String?,

    var attractions: List<Attractions>?,
    var phrases: List<String>?,

    var quiz: List<Quiz>?



)


data class Attractions(

    @SerializedName("type")
    val type: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("cityTown")
    val cityTown: String?,
    @SerializedName("stateRegion")
    val stateRegion: String?,

    val location: Location?

)

data class Location(

    val latitude: String?,

    val longitude: String?

)

data class Quiz(

    @SerializedName("question")
    val question: String?,


    var answers: List<String>?


)

data class Phrases(

    var answers: List<String>?


)