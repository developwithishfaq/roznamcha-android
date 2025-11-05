package com.downloader.roznamcha.presentation.screens.purchases

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun PurchasesScreen(
    viewModel: PurchasesViewModel = koinViewModel()
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            showDialog = true
        }) {
            Text("Add")
        }
        if (showDialog){

        }
    }
}