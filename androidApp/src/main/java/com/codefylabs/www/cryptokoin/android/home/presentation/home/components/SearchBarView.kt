package com.codefylabs.www.cryptokoin.android.home.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBarView(searchText: String, onSearchTextChanged: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(searchText)) }
    Box(
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(25.dp))
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(25.dp))
            .padding(horizontal = 12.dp )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = if (text.text.isEmpty()) Color.Gray else MaterialTheme.colorScheme.primary
            )
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    onSearchTextChanged(it.text)
                },
                placeholder = { Text("Search by name or symbol...") },
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
            if (text.text.isNotEmpty()) {
                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun SearchBarViewPreview(){
    SearchBarView(searchText = "") {
        
    }
}