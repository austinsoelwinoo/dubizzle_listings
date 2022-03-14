package com.dubizzle.listings.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dubizzle.core.domain.Listing
import com.dubizzle.listings.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

@Composable
fun ListListings(
    groupedListings: Map<String, List<Listing>>,
    isSimple: Boolean,
    onListingClick: (Listing) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        groupedListings.forEach { (date, gList) ->
            item {
                CharacterHeader(date)
            }
            items(gList) { listing ->
                ListListingItemCard(listing, isSimple, onListingClick)
            }
        }
    }
}

@Composable
fun CharacterHeader(character: String) {
    if (character.isNotEmpty()) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    character,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridListings(listings: List<Listing>, onListingClick: (Listing) -> Unit) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        content = {
            items(listings) { listing ->
                GridListingItemCard(listing, onListingClick)
            }
        }
    )
}

@Composable
fun GridListingItemCard(listing: Listing, onListingClick: (Listing) -> Unit) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentHeight()
            .clickable(onClick = {
                onListingClick.invoke(listing)
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
fun ListListingItemCard(listing: Listing, isSimple: Boolean, onListingClick: (Listing) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = {
                onListingClick.invoke(listing)
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
                    .size(if (isSimple) 60.dp else 80.dp)
                    .padding(0.dp),
                contentScale = ContentScale.Fit,
            )
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = listing.name,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                )
                if (!isSimple) {
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


@Composable
fun PlaceholderListing(modifier: Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.listing_item_placeholder),
                contentDescription = "",
                modifier = modifier
                    .size(60.dp)
                    .padding(0.dp),
                contentScale = ContentScale.Fit,
            )
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = "",
                    modifier = modifier,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                )
            }
        }
    }
}

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
) {
    Row(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.listing_item_placeholder),
            contentDescription = null,
            modifier = modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp)),
        )

        Spacer(Modifier.width(16.dp))

        Text(
            text = "",
            style = MaterialTheme.typography.subtitle2,
            modifier = modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}