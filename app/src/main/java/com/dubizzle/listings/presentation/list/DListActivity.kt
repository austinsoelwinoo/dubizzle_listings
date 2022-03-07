package com.dubizzle.listings.presentation.list

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
        setSupportActionBar(binding.toolbar)

        val mNoOfColumns: Int = calculateNoOfColumns(applicationContext, 130.0f)
        val mGridLayoutManager = GridLayoutManager(applicationContext, mNoOfColumns)
        val dListAdapter =
            DListAdapter(applicationContext, object : DListAdapter.ActionClickListener {
                override fun clicked(listing: Listing) {
                    val currentActivity = this@DListActivity
                    if (currentActivity.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                        currentActivity.startActivity(
                            DDetailsActivity.getCallingIntent(
                                currentActivity,
                                listing
                            )
                        )
                    }
                }
            })

        binding.recycler.adapter = dListAdapter
        binding.recycler.layoutManager = mGridLayoutManager

        val modelD: DListViewModel by viewModels { DListingsViewModelFactory }
        modelD.listings.observe(this) {
            handleViewState(false,it)
            dListAdapter.items = it
        }

        if (modelD.listings.value?.isEmpty() != false) {
            handleViewState(true)
            modelD.loadDocuments()
        }
    }

    private fun handleViewState(isLoading: Boolean, items: List<Listing> = emptyList()) {
        if (isLoading) {
            binding.pbLoading.visibility = View.VISIBLE
            binding.recycler.visibility = View.GONE
            binding.tvEmpty.visibility = View.GONE
        } else {
            binding.pbLoading.visibility = View.GONE
            binding.recycler.visibility = if (items.isNotEmpty()) View.VISIBLE else View.GONE
            binding.tvEmpty.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}
