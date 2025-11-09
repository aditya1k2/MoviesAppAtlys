package com.example.moviesappatlys.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun DebouncedTextField(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 16.dp),
    debounceTime: Long = 1000L,
    onDebouncedValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val textFlow = remember { MutableStateFlow("") }

    // Collect the debounced text value and trigger the callback
    LaunchedEffect(Unit) {
        textFlow
            .debounce(debounceTime)
            .collectLatest { value ->
                onDebouncedValueChange(value)
            }
    }

    // Update the text value and emit it to the flow
    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            textFlow.value = newText.text

        },
        placeholder = { Text("Search movies", fontSize = 16.sp, color = Color.Gray) },
        leadingIcon = {
            /*Icon(
                imageVector = ,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )*/
        },
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,  // No underline when focused
            unfocusedIndicatorColor = Color.Transparent,  // No underline when unfocused
            textColor = Color.Gray  // Text color
        ),
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7F7), RoundedCornerShape(12.dp))
    )
}

