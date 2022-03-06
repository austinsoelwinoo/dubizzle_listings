package com.dubizzle.listings.presentation.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.dubizzle.core.domain.Listing
import com.dubizzle.listings.databinding.ActivityListBinding
import com.dubizzle.listings.framework.DListingsViewModelFactory
import com.dubizzle.listings.presentation.calculateNoOfColumns
import com.dubizzle.listings.presentation.detail.DDetailsActivity


class DListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mNoOfColumns: Int = calculateNoOfColumns(applicationContext,128.0f)
        val mGridLayoutManager = GridLayoutManager(applicationContext, mNoOfColumns)
        val dListAdapter = DListAdapter(applicationContext, object : DListAdapter.ActionClickListener {
            override fun clicked(listing: Listing) {
                val currentActivity = this@DListActivity
                if (currentActivity.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                    currentActivity.startActivity(DDetailsActivity.getCallingIntent(currentActivity, listing))
                }
            }
        })
        binding.recycler.adapter = dListAdapter
        binding.recycler.layoutManager = mGridLayoutManager
        val modelD: DListViewModel by viewModels { DListingsViewModelFactory }
        modelD.listings.observe(this) {
            dListAdapter.items = it
            binding.recycler.visibility = if(it.isNotEmpty()) View.VISIBLE else View.GONE
            binding.tvEmpty.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
        }
        modelD.loadDocuments()
    }
}
