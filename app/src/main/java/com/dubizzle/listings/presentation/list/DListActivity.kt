package com.dubizzle.listings.presentation.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dubizzle.listings.databinding.ActivityMainBinding
import com.dubizzle.listings.framework.DListingsViewModelFactory
import com.dubizzle.listings.presentation.calculateNoOfColumns


class DListActivity : AppCompatActivity() {
    private val listingsAdapter = DListAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mNoOfColumns: Int = calculateNoOfColumns(applicationContext,128.0f)
        val mGridLayoutManager = GridLayoutManager(applicationContext, mNoOfColumns)
        binding.recycler.adapter = listingsAdapter
        binding.recycler.layoutManager = mGridLayoutManager
        val modelD: DListViewModel by viewModels { DListingsViewModelFactory }
        modelD.documents.observe(this) {
            listingsAdapter.items = it
        }
        modelD.loadDocuments()
    }
}
