package com.example.moviesappatlys.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomLoadingIndicator(
    modifier: Modifier = Modifier,
    message: String = "Loading...",
    progressIndicatorSize: Dp = 48.dp,
    progressIndicatorColor: Color = MaterialTheme.colors.primary,
    textStyle: TextStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(progressIndicatorSize),
                color = progressIndicatorColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = message, style = textStyle)
        }
    }
}
