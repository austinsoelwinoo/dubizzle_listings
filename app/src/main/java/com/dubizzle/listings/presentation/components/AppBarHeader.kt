package com.dubizzle.listings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dubizzle.listings.presentation.list.DListViewModel

@Composable
fun AppBarHeader(state: DListViewModel) {
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
                    value = state.text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(0.dp)
                        .background(Color.Transparent),
                    maxLines = 1,
                    onValueChange = { state.text = it },
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
                        shape = MaterialTheme.shapes.small.copy(CornerSize(10))
                    )
                    .padding(horizontal = 8.dp)
            ) {
                OptionDropdown(
                    this,
                    listOf(
                        UIOption.SORT_DATE_O_N,
                        UIOption.SORT_DATE_N_O,
                        UIOption.SORT_PRICE_H_L,
                        UIOption.SORT_PRICE_L_H
                    ),
                    state.sortOptionItem
                ) { state.sortOptionItem = it }
            }

            Spacer(
                modifier = Modifier
                    .weight(0.02f)
                    .padding(horizontal = 4.dp)
                    .fillMaxHeight()
                    .background(Color.LightGray)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = Color.LightGray,
                        shape = MaterialTheme.shapes.small.copy(CornerSize(10))
                    )
                    .padding(horizontal = 8.dp)
            ) {
                OptionDropdown(
                    this,
                    listOf(
                        UIOption.DISPLAY_SIMPLE,
                        UIOption.DISPLAY_DETAILED,
                        UIOption.DISPLAY_GRID,
                    ),
                    state.displayOptionItem
                ) { state.displayOptionItem = it }
            }
        }
        GroupByCheckBox(state.isGroupChecked) { state.isGroupChecked = it }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )
    }
}