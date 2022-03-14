package com.dubizzle.listings.presentation.list

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import com.dubizzle.core.domain.Listing
import com.dubizzle.listings.R
import com.dubizzle.listings.presentation.components.*
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
        listings: List<Listing>,
        displayOptionItem: UIOption,
        onListingClick: (Listing) -> Unit
    ) {
        if (listings.isNotEmpty()) {
            when (displayOptionItem) {
                UIOption.DISPLAY_DETAILED -> ListListings(listings, false, onListingClick)
                UIOption.DISPLAY_GRID -> GridListings(listings, onListingClick)
                else -> ListListings(listings, true, onListingClick)
            }
        }
    }

}

enum class UIOption(val label: String) {
    DISPLAY_SIMPLE("Simple List"),
    DISPLAY_DETAILED("Detailed List"),
    DISPLAY_GRID("Image Grid"),

    GROUP_BY_DATE("Group by date"),

    SORT_PRICE_H_L("Price Highest to Lowest"),
    SORT_PRICE_L_H("Price Lowest to Highest"),
    SORT_DATE_O_N("Oldest to Newest"),
    SORT_PRICE_N_O("Newest to Oldest"),
}