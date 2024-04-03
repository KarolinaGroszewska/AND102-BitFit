package com.example.bitfit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bitfit.databinding.FragmentAddJournalBinding
import java.text.SimpleDateFormat
import java.util.Date

class AddJournalFragment : Fragment() {

    private lateinit var binding: FragmentAddJournalBinding
    private lateinit var journal: Journal
    private lateinit var oldJournal: Journal
    private var isUpdate = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            oldJournal = arguments?.getSerializable("current_journal") as Journal
            binding.etTitle.setText(oldJournal.title)
            binding.etNote.setText(oldJournal.note)
            isUpdate = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val journalDescription = binding.etNote.text.toString()

            if (title.isNotEmpty() && journalDescription.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if (isUpdate) {
                    journal = Journal(oldJournal.id, title, journalDescription, formatter.format(
                        Date()
                    ))
                } else {
                    journal = Journal(null, title, journalDescription, formatter.format(Date()))
                }
                val intent = Intent()
                intent.putExtra("journal", journal)
                activity?.setResult(Activity.RESULT_OK, intent)
                activity?.finish()
            } else {
                Toast.makeText(requireContext(), "Please enter some data", Toast.LENGTH_LONG).show()
            }
        }

        binding.imgDelete.setOnClickListener {
            val intent = Intent()
            intent.putExtra("journal", oldJournal)
            intent.putExtra("delete_journal", true)
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }

        binding.imgBackArrow.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}
