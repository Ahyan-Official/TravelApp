package com.travelingapp.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.travelingapp.model.dao.LanguagesDao

import com.travelingapp.model.room.AppLanguage

@Database(entities = [AppLanguage::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getLanguagesDao(): LanguagesDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
                AppDatabase::class.java, "translator.db").build()
    }
}