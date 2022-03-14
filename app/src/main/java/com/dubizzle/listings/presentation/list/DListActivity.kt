package com.dubizzle.listings.presentation.list

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dubizzle.core.domain.Listing
import com.dubizzle.listings.R
import com.dubizzle.listings.presentation.components.*
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
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
            if (state.isLoading) {
                LazyColumn {
                    items(10) { index ->
                        ListItem(
                            Modifier.placeholder(
                                highlight = PlaceholderHighlight.shimmer(),
                                visible = true
                            )
                        )
                    }
                }
            } else {
                val groupedListings = state.result
                val displayOptionItem = state.displayOptionItem
                val onListingClick: (Listing) -> Unit = { listing ->
                    Timber.d("onListingClick $listing")//TODO link to detail
                }
                if (groupedListings.values.flatten().isNotEmpty()) {
                    when (displayOptionItem) {
                        UIOption.DISPLAY_DETAILED -> ListListings(
                            groupedListings,
                            false,
                            onListingClick
                        )
                        UIOption.DISPLAY_GRID -> GridListings(
                            groupedListings.values.flatten(), onListingClick
                        )
                        else -> ListListings(groupedListings, true, onListingClick)
                    }
                } else {
                    Text(
                        text = "No listings found",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                    )
                }
            }
        }
    }

    @Composable
    fun LiveDataComponent(
        groupedListings: Map<String, List<Listing>>,
        displayOptionItem: UIOption,
        onListingClick: (Listing) -> Unit
    ) {

    }

}
