package com.dubizzle.listings.presentation.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.dubizzle.listings.R
import com.dubizzle.listings.framework.DListingsViewModelFactory

class DListActivity : AppCompatActivity() {
    private val listingsAdapter = DListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.recycler).adapter = listingsAdapter

        val modelD: DListViewModel by viewModels { DListingsViewModelFactory }
        modelD.documents.observe(this) {
            listingsAdapter.items = it
        }
        modelD.loadDocuments()
    }
}
