package com.example.bitfit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitfit.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home), JournalAdapter.JournalClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: JournalAdapter
    private lateinit var viewModel: JournalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and ViewModel
        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        adapter = JournalAdapter(requireContext(), this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(JournalViewModel::class.java)

        viewModel.allJournal.observe(viewLifecycleOwner) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }
    }

    private val updateOrDeleteTodo =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val journal = result.data?.getSerializableExtra("journal") as Journal
                val isDelete = result.data?.getBooleanExtra("delete_journal", false) as Boolean
                if (journal != null && !isDelete) {
                    viewModel.updateJournal(journal)
                } else if (journal != null && isDelete) {
                    viewModel.deleteJournal(journal)
                }
            }
        }

    override fun onItemClicked(journal: Journal) {
        var fr = fragmentManager?.beginTransaction()
        fr?.replace(R.id.fragment_container_view, AddJournalFragment())
        fr?.commit()
    }
}