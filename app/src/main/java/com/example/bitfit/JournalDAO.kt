package com.example.bitfit

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface JournalDao {
    @Insert
    suspend fun insert(journal: Journal)

    @Delete
    suspend fun delete(journal: Journal)

    @Query("SELECT * from journal_table order by id ASC")
    fun getAllJournals(): LiveData<List<Journal>>

    @Query("UPDATE journal_table set title = :title, text = :note where id = :id")
    suspend fun update(id: Int?, title: String?, note: String?)
}