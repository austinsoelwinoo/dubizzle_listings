package com.dubizzle.listings.presentation.list

import android.os.Bundle
import com.dubizzle.listings.R
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import com.dubizzle.core.domain.Listing
import org.koin.androidx.viewmodel.ext.android.viewModel

class DListActivity : AppCompatActivity() {
    private val modelD: DListViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiveDataComponent(modelD.listings)
        }
        modelD.loadDocuments()
    }

    @Composable
    fun LiveDataComponent(listingsLiveData: LiveData<List<Listing>>) {
        val listings by listingsLiveData.observeAsState(initial = emptyList())
        if (listings.isNotEmpty()) {
            ListingList(listings)
        }
    }

    @Composable
    fun ListingList(listings: List<Listing>) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(listings) { listing ->
                PlantCard(listing.name, listing.price, listing.retrieveFirstImageThumbnail())
            }
        }
    }

    @Composable
    fun PlantCard(name: String, description: String, imageUrl: String) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
            elevation = 5.dp,
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = imageUrl,
                    placeholder = painterResource(id = R.drawable.listing_item_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(0.dp),
                    contentScale = ContentScale.Fit,
                )
                Column(Modifier.padding(8.dp)) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface,
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
}
