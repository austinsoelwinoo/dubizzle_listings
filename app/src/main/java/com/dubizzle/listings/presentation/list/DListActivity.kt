package com.dubizzle.listings.presentation.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dubizzle.listings.databinding.ActivityMainBinding
import com.dubizzle.listings.framework.DListingsViewModelFactory

class DListActivity : AppCompatActivity() {
    private val listingsAdapter = DListAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = listingsAdapter
        val modelD: DListViewModel by viewModels { DListingsViewModelFactory }
        modelD.documents.observe(this) {
            listingsAdapter.items = it
        }
        modelD.loadDocuments()
    }
}
