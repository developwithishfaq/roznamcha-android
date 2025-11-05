package com.downloader.roznamcha.presentation.sheets.persons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.downloader.roznamcha.data.models.PersonToDeal
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonBottomSheet(
    onPersonSelected: (PersonToDeal) -> Unit,
    onDismiss: () -> Unit,
    viewModel: PersonToDealViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var name by remember { mutableStateOf("") }
    var khata by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Prefill edit fields
    LaunchedEffect(state.selectedPerson) {
        state.selectedPerson?.let { person ->
            name = person.name
            khata = person.khataNumber.toString()
            role = person.role
            description = person.description
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadPersons()
    }

    ModalBottomSheet(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.search(it) },
                label = { Text("Search Person") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { viewModel.showAddNew() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add New")
            }

            when {
                state.isAddingNew || state.isEditing -> {
                    val isEditMode = state.isEditing
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = name,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { name = it },
                        label = { Text("Name") }
                    )
                    OutlinedTextField(
                        value = khata, modifier = Modifier.fillMaxWidth(),
                        onValueChange = { khata = it },
                        label = { Text("Khata Number") }
                    )
                    OutlinedTextField(
                        value = role,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { role = it },
                        label = { Text("Role") }
                    )
                    OutlinedTextField(
                        value = description,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { description = it },
                        label = { Text("Description") }
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                if (isEditMode) {
                                    viewModel.updatePerson(
                                        personId = state.selectedPerson!!.personId,
                                        name = name,
                                        khataNumber = khata.toIntOrNull() ?: 0,
                                        role = role,
                                        description = description
                                    )
                                } else {
                                    viewModel.savePerson(
                                        name = name,
                                        khataNumber = khata.toIntOrNull() ?: 0,
                                        role = role,
                                        description = description
                                    )
                                }
                                name = ""; khata = ""; role = ""; description = ""
                            }
                        ) {
                            Text(if (isEditMode) "Update" else "Save")
                        }
                        OutlinedButton(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.cancelForm()
                                name = ""; khata = ""; role = ""; description = ""
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                }

                else -> {
                    LazyColumn {
                        items(state.persons) { person ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onPersonSelected(person) }
                                    .padding(vertical = 8.dp, horizontal = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = person.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "Khata: ${person.khataNumber}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                        )
                                    )
                                    Text(
                                        text = "Role: ${person.role}",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    )
                                }

                                // ✅ Edit button (doesn't trigger selection)
                                TextButton(
                                    onClick = { viewModel.showEdit(person) },
                                    modifier = Modifier.padding(start = 8.dp)
                                ) {
                                    Text("Edit")
                                }
                            }

                            Divider(
                                modifier = Modifier.padding(vertical = 4.dp),
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                            )
                        }
                    }

                }
            }
        }
    }
}
