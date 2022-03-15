package com.dubizzle.listings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GroupByCheckBox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        Row {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.align(Alignment.CenterVertically),
                colors = CheckboxDefaults.colors(Color.Red)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                UIOption.GROUP_BY_DATE.label,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun OptionDropdown(
    boxScope: BoxScope,
    items: List<UIOption>,
    selectedItem: UIOption,
    onSortOptionItemChanged: (UIOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    boxScope.apply {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center)
                .clickable(onClick = { expanded = true })
        ) {
            Text(
                selectedItem.label,
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
                    onSortOptionItemChanged.invoke(items[index])
                    expanded = false
                }) {
                    Text(text = s.label)
                }
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
    SORT_DATE_N_O("Newest to Oldest"),
}