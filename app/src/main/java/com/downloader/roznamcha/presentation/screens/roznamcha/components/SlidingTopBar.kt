package com.downloader.roznamcha.presentation.screens.roznamcha.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun SlidingTopBar(
    modifier: Modifier = Modifier,
    text: String,
    onNext: () -> Unit,
    onBack: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = "Back",
            modifier = Modifier.clickable {
                onBack.invoke()
            })
        Text(
            text = text,
            fontSize = 20.sp
        )
        Icon(
            imageVector = Icons.Filled.ArrowForwardIos,
            contentDescription = "Forward",
            modifier = Modifier.clickable {
                onNext.invoke()
            })
    }
}