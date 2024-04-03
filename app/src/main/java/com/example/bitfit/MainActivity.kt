package com.example.bitfit

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitfit.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_page -> {
                    // Replace the current fragment with HomeFragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, HomeFragment())
                        .commit()
                    true
                }
                R.id.add_page -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, AddJournalFragment())
                        .commit()
                    true
                }
                R.id.summary_page -> {
                    // Replace the current fragment with SummaryFragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, SummaryFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

        // Set the default fragment (HomeFragment)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, HomeFragment())
            .commit()
    }
}
