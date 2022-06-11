package com.travelingapp.model.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.travelingapp.api.ApiHelperTranslation
import com.travelingapp.model.dao.LanguagesDao
import com.travelingapp.model.db.AppDatabase
import com.travelingapp.model.room.AppLanguage
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class LanguagesViewModel(application: Application) : AndroidViewModel(application) {
    private var languagesDao: LanguagesDao = AppDatabase.invoke(application).getLanguagesDao()
    @ObsoleteCoroutinesApi
    var coroutineContext: ExecutorCoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    fun getAllLanguages(): LiveData<List<AppLanguage>> {
        return languagesDao.load()
    }

    @ObsoleteCoroutinesApi
    suspend fun loadLanguages() {
        withContext(coroutineContext) {
            val map = ApiHelperTranslation().getLanguagesAsync().await().body()?.langs
            val result = mutableListOf<AppLanguage>()
            if (map != null) {
                for ((k, v) in map) {
                    result.add(AppLanguage(k, v))
                }
            }
            saveLanguage(result)
        }
    }

    @ObsoleteCoroutinesApi
    suspend fun saveLanguage(languages: List<AppLanguage>) {
        withContext(coroutineContext) {
            languagesDao.save(languages)
        }
    }
}