package com.example.bitfit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JournalViewModel(application: Application): AndroidViewModel(application) {
    private val repository: JournalRepository
    val allJournal : LiveData<List<Journal>>

    init {
        val dao = JournalDatabase.getDatabase(application).getJournalDao()
        repository = JournalRepository(dao)
        allJournal = repository.allJournals
    }

    fun insertJournal(journal: Journal) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(journal)
    }

    fun updateJournal(journal: Journal) = viewModelScope.launch(Dispatchers.IO){
        repository.update(journal)
    }

    fun deleteJournal(journal: Journal) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(journal)
    }
}