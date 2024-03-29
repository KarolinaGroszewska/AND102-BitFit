package com.example.bitfit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import com.example.bitfit.databinding.ActivityAddJournalBinding


class AddJournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddJournalBinding
    private lateinit var journal: Journal
    private lateinit var oldJournal: Journal
    var isUpdate = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldJournal = intent.getSerializableExtra("current_journal") as Journal
            binding.etTitle.setText(oldJournal.title)
            binding.etNote.setText(oldJournal.note)
            isUpdate = true
        }catch (e: Exception){
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val journalDescription = binding.etNote.text.toString()

            if(title.isNotEmpty() && journalDescription.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if(isUpdate){
                    journal = Journal(oldJournal.id, title, journalDescription, formatter.format(Date()))
                }else{
                    journal = Journal(null, title, journalDescription, formatter.format(Date()))
                }
                var intent = Intent()
                intent.putExtra("journal", journal)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }else{
                Toast.makeText(this@AddJournalActivity, "please enter some data", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }

        binding.imgDelete.setOnClickListener {
            var intent = Intent()
            intent.putExtra("journal", oldJournal)
            intent.putExtra("delete_journal", true)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }
    }
}