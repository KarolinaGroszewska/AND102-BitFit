package com.example.bitfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bitfit.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding
    private lateinit var viewModel: JournalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(JournalViewModel::class.java)

        viewModel.allJournal.observe(viewLifecycleOwner) { journalList ->
            // Update UI with the summary information
            updateSummary(journalList)
        }
    }

    private fun updateSummary(journalList: List<Journal>) {
        // Set the number of entries
        binding.textViewEntryCount.text = "Number of Entries: ${journalList.size}"

        // Set the most recent entry
        if (journalList.isNotEmpty()) {
            val mostRecentEntry = journalList.maxByOrNull { it.date }
            mostRecentEntry?.let {
                binding.textViewRecentEntry.text = it.title
                binding.textViewRecentEntryDateInfo.text = it.date
            }
        } else {
            binding.textViewRecentEntry.text = "No entries yet"
            binding.textViewRecentEntryDate.text = ""
            binding.textViewRecentEntryDateInfo.text = ""
        }
    }
}
