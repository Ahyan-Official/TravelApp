package com.travelingapp

import com.travelingapp.arrayJSON.ArrayJSONModel

import retrofit2.Response
import retrofit2.http.GET

interface APIServiceData {

    @GET("/nabz123/a05714149e753a8eaa70b568a80e0be0/raw/e79a710760da5c75c49ab4462939a9af921428e2/travelapp2.json")
    suspend fun getEmployees(): Response<List<ArrayJSONModel>>



}