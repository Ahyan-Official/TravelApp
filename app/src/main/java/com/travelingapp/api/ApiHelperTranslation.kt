package com.travelingapp.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.travelingapp.model.api.LanguageDetectionResult
import com.travelingapp.model.api.TranslationDirs
import com.travelingapp.model.api.TranslationResult
import com.travelingapp.utils.Constants.API_BASE_URL
import com.travelingapp.utils.Constants.API_KEY

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class ApiHelperTranslation {
    private val mService: ApiServiceTranslation

    init {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(API_BASE_URL)
                .build()
        mService = retrofit.create(ApiServiceTranslation::class.java)
    }

    fun translateAsync(text: String, lang: String): Deferred<Response<TranslationResult>> {
        return mService.translateAsync(API_KEY, text, lang)
    }

    fun detectAsync(text: String): Deferred<Response<LanguageDetectionResult>> {
        return mService.detectLanguageAsync(API_KEY, text)
    }

    fun getLanguagesAsync(): Deferred<Response<TranslationDirs>> {
        return mService.getLangsAsync(API_KEY, Locale.getDefault().language)
    }
}
