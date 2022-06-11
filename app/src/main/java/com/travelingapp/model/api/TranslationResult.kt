package com.travelingapp.model.api



data class TranslationResult(
        var code: Int,
        var lang: String,
        var text: List<String>
)
