package com.dubizzle.listings.presentation.list

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.dubizzle.core.domain.Listing
import com.dubizzle.listings.presentation.components.AppBarHeader
import com.dubizzle.listings.presentation.components.GridListings
import com.dubizzle.listings.presentation.components.ListListings
import com.dubizzle.listings.presentation.components.UIOption
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import timber.log.Timber

class DListActivity : AppCompatActivity() {
    private val viewModel: DListViewModel by stateViewModel()
    var text = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ScreenContent(state = viewModel)
            }
        }
        viewModel.loadDocuments()
    }

    @Composable
    fun ScreenContent(state: DListViewModel) {
        Column {
            AppBarHeader(state = state)
            LiveDataComponent(state.result, state.displayOptionItem) { listing ->
                Timber.d("onListingClick $listing")
            }
        }
    }

    @Composable
    fun LiveDataComponent(
        groupedListings: Map<String, List<Listing>>,
        displayOptionItem: UIOption,
        onListingClick: (Listing) -> Unit
    ) {
        if (groupedListings.isNotEmpty()) {
            when (displayOptionItem) {
                UIOption.DISPLAY_DETAILED -> ListListings(groupedListings, false, onListingClick)
                UIOption.DISPLAY_GRID -> GridListings(
                    groupedListings.values.firstOrNull() ?: emptyList(), onListingClick
                )
                else -> ListListings(groupedListings, true, onListingClick)
            }
        }
    }

}
