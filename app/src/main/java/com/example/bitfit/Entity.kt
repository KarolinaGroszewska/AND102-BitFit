package com.example.bitfit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_table")
data class Journal(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "text") val note: String?,
    @ColumnInfo(name = "date") val date: String
): java.io.Serializable