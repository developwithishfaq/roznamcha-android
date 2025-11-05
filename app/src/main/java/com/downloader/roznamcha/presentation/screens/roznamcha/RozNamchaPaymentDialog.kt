package com.downloader.roznamcha.presentation.screens.roznamcha

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.presentation.sheets.persons.PersonBottomSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun RozNamchaPaymentDialog(
    onDismiss: () -> Unit,
    onSaved: () -> Unit,
    viewModel: RoznamchaViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showPersonsSheet by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                if (state.isEditMode)
                    "Update Roznamcha Entry"
                else
                    "Add Roznamcha Entry"
            )
        },
        text = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                if (showPersonsSheet) {
                    PersonBottomSheet(
                        onPersonSelected = {
                            showPersonsSheet = false
                            viewModel.selectPerson(it)
                        },
                        onDismiss = {
                            showPersonsSheet = false
                        }
                    )
                }
                OutlinedTextField(
                    value = state.amount,
                    onValueChange = { viewModel.updateAmount(it) },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Type:")
                    FilterChip(
                        selected = state.isMyIncome,
                        onClick = { viewModel.toggleIncome(true) },
                        label = { Text("Income") }
                    )
                    FilterChip(
                        selected = !state.isMyIncome,
                        onClick = { viewModel.toggleIncome(false) },
                        label = { Text("Expense") }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "Person: ${state.personToDeal?.name ?: "-- Not Selected --"}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        state.personToDeal?.let {
                            Text(
                                text = "Khata #: ${it.khataNumber}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Button(
                        onClick = { showPersonsSheet = true },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Select")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.saveOrUpdate {
                        onSaved()
                        viewModel.reset()
                    }
                }
            ) {
                Text(if (state.isEditMode) "Update" else "Save")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = {
                viewModel.reset()
                onDismiss()
            }) {
                Text("Cancel")
            }
        }
    )
}
