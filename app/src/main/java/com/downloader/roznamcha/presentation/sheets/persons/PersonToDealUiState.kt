package com.downloader.roznamcha.presentation.sheets.persons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.repository.PersonToDealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

data class PersonToDealUiState(
    val persons: List<PersonToDeal> = emptyList(),
    val filtered: List<PersonToDeal> = emptyList(),
    val searchQuery: String = "",
    val isAddingNew: Boolean = false,
    val isEditing: Boolean = false,
    val selectedPerson: PersonToDeal? = null,
)


class PersonToDealViewModel(
    private val repo: PersonToDealRepository,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {

    private val _state = MutableStateFlow(PersonToDealUiState())
    val state = _state.asStateFlow()

    fun loadPersons() {
        viewModelScope.launch {
            val bId = preferencesHelper.businessIdFlow.first() ?: ""
            repo.getByBusiness(bId).collectLatest { persons ->
                _state.update { it.copy(persons = persons, filtered = persons) }
            }
        }
    }

    fun search(query: String) {
        val q = query.lowercase()
        val list = _state.value.persons.filter {
            q.isBlank() || it.name.lowercase().contains(q)
        }
        _state.update { it.copy(filtered = list) }
    }

    fun filteredPersons(): List<PersonToDeal> {
        val q = _state.value.searchQuery.lowercase()
        return _state.value.persons.filter {
            q.isBlank() || it.name.lowercase().contains(q)
        }
    }

    fun showAddNew() {
        _state.update { it.copy(isAddingNew = true, isEditing = false, selectedPerson = null) }
    }

    fun showEdit(person: PersonToDeal) {
        _state.update {
            it.copy(
                selectedPerson = person,
                isEditing = true,
                isAddingNew = false
            )
        }
    }

    fun savePerson(
        name: String,
        khataNumber: Int,
        role: String,
        description: String,
    ) {
        viewModelScope.launch {
            val businessId = preferencesHelper.businessIdFlow.first() ?: ""
            val newPerson = PersonToDeal(
                personId = UUID.randomUUID().toString(),
                khataNumber = khataNumber,
                name = name,
                role = role,
                description = description,
                businessId = businessId,
                creationTime = System.currentTimeMillis(),
                updateTime = System.currentTimeMillis()
            )
            repo.insert(newPerson)
            _state.update { it.copy(isAddingNew = false) }
        }
    }

    fun updatePerson(
        personId: String,
        name: String,
        khataNumber: Int,
        role: String,
        description: String,
    ) {
        viewModelScope.launch {
            val old = _state.value.persons.find { it.personId == personId } ?: return@launch
            val updated = old.copy(
                name = name,
                khataNumber = khataNumber,
                role = role,
                description = description,
                updateTime = System.currentTimeMillis()
            )
            repo.update(updated)
            _state.update { it.copy(isEditing = false, selectedPerson = null) }
        }
    }

    fun cancelForm() {
        _state.update { it.copy(isAddingNew = false, isEditing = false, selectedPerson = null) }
    }
}
