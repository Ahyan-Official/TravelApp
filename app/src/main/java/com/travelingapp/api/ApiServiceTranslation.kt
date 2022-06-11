package com.travelingapp.api

import com.travelingapp.model.api.LanguageDetectionResult
import com.travelingapp.model.api.TranslationDirs
import com.travelingapp.model.api.TranslationResult

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceTranslation {
    @GET("getLangs")
    fun getLangsAsync(@Query("key") key: String,
                      @Query("ui") ui: String): Deferred<Response<TranslationDirs>>

    @GET("detect")
    fun detectLanguageAsync(@Query("key") key: String,
                            @Query("text") text: String): Deferred<Response<LanguageDetectionResult>>

    @GET("translate")
    fun translateAsync(@Query("key") key: String,
                       @Query("text") text: String,
                       @Query("lang") lang: String): Deferred<Response<TranslationResult>>
}
