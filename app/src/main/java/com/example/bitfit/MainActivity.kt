package com.example.bitfit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitfit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), JournalAdapter.JournalClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: JournalDatabase
    lateinit var viewModel: JournalViewModel
    lateinit var adapter: JournalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(JournalViewModel::class.java)

        viewModel.allJournal.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }

        database = JournalDatabase.getDatabase(this)
    }

    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = JournalAdapter(this, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.isNestedScrollingEnabled = false;


        val getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val journal = result.data?.getSerializableExtra("journal") as? Journal
                    if (journal != null) {
                        viewModel.insertJournal(journal)
                    }
                }
            }

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_page -> {
                    // Handle home page navigation
                    true
                }
                R.id.summary_page -> {
                    // Handle summary page navigation
                    true
                }
                R.id.add_page -> {
                    val intent = Intent(this, AddJournalActivity::class.java)
                    getContent.launch(intent)
                    true
                }
                else -> false
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
        val intent = Intent(this@MainActivity, AddJournalActivity::class.java)
        intent.putExtra("current_journal", journal)
        updateOrDeleteTodo.launch(intent)
    }
}