package com.travelingapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.travelingapp.model.room.AppLanguage

@Dao
interface LanguagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(language: List<AppLanguage>)

    @Query("SELECT * FROM languages ORDER BY lang ASC")
    fun load(): LiveData<List<AppLanguage>>
}