package com.travelingapp

import android.app.Application
import com.travelingapp.model.view.LanguagesViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch

class App : Application() {
   // @ObsoleteCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        val application = this
        GlobalScope.launch {
            LanguagesViewModel(application).loadLanguages()
        }
    }
}