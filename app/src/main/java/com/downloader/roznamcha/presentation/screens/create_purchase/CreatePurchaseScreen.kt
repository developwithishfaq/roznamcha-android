package com.downloader.roznamcha.presentation.screens.create_purchase

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePurchaseScreen(
    modifier: Modifier = Modifier,
    viewModel: CreatePurchaseViewModel = koinViewModel(),
    onPurchaseCreated: () -> Unit = {},
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Create Purchase") },
            )
        }) { inner ->
        Column(
            modifier = modifier
                .padding(inner)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = viewModel.dealerName,
                onValueChange = { viewModel.dealerName = it },
                label = { Text("Dealer Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.dealerKhata,
                onValueChange = { viewModel.dealerKhata = it },
                label = { Text("Dealer Khata Number") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = viewModel.driverName,
                onValueChange = { viewModel.driverName = it },
                label = { Text("Driver Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.driverKhata,
                onValueChange = { viewModel.driverKhata = it },
                label = { Text("Driver Khata Number") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = viewModel.itemWeight,
                onValueChange = { viewModel.itemWeight = it },
                label = { Text("Item Weight (kg)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                value = viewModel.perKgPrice,
                onValueChange = { viewModel.perKgPrice = it },
                label = { Text("Per Kg Price (Rs)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                value = viewModel.perKgDriverWage,
                onValueChange = { viewModel.perKgDriverWage = it },
                label = { Text("Driver Wage / Kg (Rs)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { viewModel.showDatePicker = true }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Purchase Date")
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.createPurchase(businessId = "YOUR_BUSINESS_ID")
                    Toast.makeText(context, "Purchase saved!", Toast.LENGTH_SHORT).show()
                    onPurchaseCreated()
                }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save Purchase")
            }
        }

        if (viewModel.showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { viewModel.showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.showDatePicker = false
                    }) {
                        Text("OK")
                    }
                }) {
                DatePicker(
                    state = rememberDatePickerState(
                        initialSelectedDateMillis = viewModel.purchaseTime
                    )
                )
            }
        }
    }
}
