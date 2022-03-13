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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import com.dubizzle.core.domain.Listing
import com.dubizzle.listings.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DListActivity : AppCompatActivity() {
    private val modelD: DListViewModel by viewModel()
    var text = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppBars()
            }
            //LiveDataComponent(modelD.listings)
        }
        modelD.loadDocuments()
    }

    @Composable
    fun AppBars() {
        var value by remember { mutableStateOf(TextFieldValue("")) }
        Column {
            Surface(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(0.dp)
                            .background(Color.Transparent),
                        maxLines = 1,
                        onValueChange = { value = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search,
                        ),
                        shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 10)),
                        placeholder = { Text(text = "Search product by name") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = ""
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.LightGray,
                            focusedBorderColor = Color.LightGray,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1.5f)
                        .background(
                            color = Color.LightGray,
                            shape = MaterialTheme.shapes.small.copy(CornerSize(20))
                        )
                        .padding(horizontal = 8.dp)
                ) {
                    DropdownDemo(
                        this,
                        listOf(
                            UIOption.SORT_DATE_O_N,
                            UIOption.SORT_PRICE_N_O,
                            UIOption.SORT_PRICE_H_L,
                            UIOption.SORT_PRICE_L_H
                        )
                    )
                }

                Spacer(
                    modifier = Modifier
                        .weight(0.01f)
                        .padding(horizontal = 4.dp)
                        .fillMaxHeight()
                        .background(Color.LightGray)
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = Color.LightGray,
                            shape = MaterialTheme.shapes.small.copy(CornerSize(20))
                        )
                        .padding(horizontal = 8.dp)
                ) {
                    DropdownDemo(
                        this,
                        listOf(
                            UIOption.DISPLAY_SIMPLE,
                            UIOption.DISPLAY_ADVANCED,
                            UIOption.DISPLAY_GRID,
                        )
                    )
                }
            }
            CheckBoxDemo()
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
            )
        }
    }

    @Composable
    fun CheckBoxDemo() {
        Column(
            modifier = Modifier.fillMaxWidth().height(56.dp),
        ) {
            Row {
                val isChecked = remember { mutableStateOf(false) }
                Checkbox(
                    checked = isChecked.value,
                    onCheckedChange = {
                        isChecked.value = it
                    },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    colors = CheckboxDefaults.colors(Color.Red)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(UIOption.GROUP_BY_DATE.label, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
    }

    @Composable
    fun DropdownDemo(boxScope: BoxScope, items: List<UIOption>) {
        var expanded by remember { mutableStateOf(false) }
        var selectedIndex by remember { mutableStateOf(2) }

        boxScope.apply {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.Center)
                    .clickable(onClick = { expanded = true })
            ) {
                Text(
                    items[selectedIndex].label,
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterStart),
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    "",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(24.dp)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .background(
                        Color.LightGray
                    )
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                    }) {
                        Text(text = s.label)
                    }
                }
            }
        }
    }

    @Composable
    fun LiveDataComponent(listingsLiveData: LiveData<List<Listing>>) {
        val listings by listingsLiveData.observeAsState(initial = emptyList())
        if (listings.isNotEmpty()) {
            LazyVerticalGridDemo(listings)
        }
    }

    @Composable
    fun ListingList(listings: List<Listing>) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(listings) { listing ->
                PlantCard(listing)
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun LazyVerticalGridDemo(listings: List<Listing>) {
        LazyVerticalGrid(
            cells = GridCells.Adaptive(160.dp),

            // content padding
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(listings) { listing ->
                    GridListingCard(listing)
                }
            }
        )
    }

    @Composable
    fun GridListingCard(listing: Listing) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentHeight()
                .clickable(onClick = {
                    Timber.d("Tapped $listing")
                }),
            shape = MaterialTheme.shapes.medium,
            elevation = 1.dp,
            backgroundColor = MaterialTheme.colors.surface
        ) {
            AsyncImage(
                model = listing.retrieveFirstImageThumbnail(),
                placeholder = painterResource(id = R.drawable.listing_item_placeholder),
                contentDescription = null,
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(0.dp),
                contentScale = ContentScale.Fit,
            )
        }
    }

    @Composable
    fun PlantCard(listing: Listing) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable(onClick = {
                    Timber.d("Tapped $listing")
                }),
            shape = MaterialTheme.shapes.medium,
            elevation = 5.dp,
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = listing.retrieveFirstImageThumbnail(),
                    placeholder = painterResource(id = R.drawable.listing_item_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(0.dp),
                    contentScale = ContentScale.Fit,
                )
                Column(Modifier.padding(8.dp)) {
                    Text(
                        text = listing.name,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface,
                    )
                    Text(
                        text = listing.price,
                        style = MaterialTheme.typography.body2,
                    )
                    Text(
                        text = listing.prettifiedCreatedAt(),
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
}

enum class UIOption(val label: String) {
    DISPLAY_SIMPLE("Simple List"),
    DISPLAY_ADVANCED("Advanced List"),
    DISPLAY_GRID("Grid"),

    GROUP_BY_DATE("Group by date"),

    SORT_PRICE_H_L("Price Highest to Lowest"),
    SORT_PRICE_L_H("Price Lowest to Highest"),
    SORT_DATE_O_N("Oldest to Newest"),
    SORT_PRICE_N_O("Newest to Oldest"),
}