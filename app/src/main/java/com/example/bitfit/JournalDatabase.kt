package com.example.bitfit

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bitfit.JournalDatabase

@Database(entities = arrayOf(Journal::class), version = 1)
abstract class JournalDatabase : RoomDatabase() {
    abstract fun getJournalDao(): JournalDao

    companion object {
        @Volatile
        private var INSTANCE: JournalDatabase? = null

        fun getDatabase(context: Context): JournalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JournalDatabase::class.java,
                    "journal_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }

}