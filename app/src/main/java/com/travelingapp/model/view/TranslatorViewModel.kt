package com.travelingapp.model.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.travelingapp.api.ApiHelperTranslation
import com.travelingapp.model.api.LanguageDetectionResult
import com.travelingapp.model.api.TranslationResult

class TranslatorViewModel(application: Application) : AndroidViewModel(application) {

    suspend fun translate(text: String, dir: Array<String?>): TranslationResult? {
        val dirStr = "${dir[0]}-${dir[1]}"
        return ApiHelperTranslation().translateAsync(text, dirStr).await().body()
    }

    suspend fun translate(text: String, dir: String?): TranslationResult? {
        return ApiHelperTranslation().translateAsync(text, dir!!).await().body()
    }

    suspend fun detectLanguage(text: String): LanguageDetectionResult? {
        return ApiHelperTranslation().detectAsync(text).await().body()
    }
}