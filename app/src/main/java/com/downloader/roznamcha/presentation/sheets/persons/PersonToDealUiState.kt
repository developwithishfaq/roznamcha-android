package com.downloader.roznamcha.presentation.sheets.persons

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.repository.PersonToDealRepository
import com.downloader.roznamcha.domain.usecases.CreatePersonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
    private val preferencesHelper: PreferencesHelper,
    private val createPersonUseCase: CreatePersonUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PersonToDealUiState())
    val state = _state.asStateFlow()

    fun loadPersons() {
        viewModelScope.launch {
            val bId = preferencesHelper.businessIdFlow.first() ?: ""
            repo.getByBusiness(bId).collectLatest { persons ->
                Log.d("cvv", "loadPersons: ${persons.size}")
                val q = state.value.searchQuery.lowercase()
                _state.update {
                    it.copy(persons = persons, filtered = persons.filter { p ->
                        q.isBlank() || p.name.lowercase().contains(q)
                    })
                }
            }
        }
    }

    fun search(query: String) {
        val q = query.lowercase()
        val list = _state.value.persons.filter {
            q.isBlank() || it.name.lowercase().contains(q)
        }
        _state.update { it.copy(filtered = list, searchQuery = query) }
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
        success: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            if (createPersonUseCase.invoke(name, khataNumber)) {
                _state.update { it.copy(isAddingNew = false) }
                success.invoke(true)
            } else {
                success.invoke(false)
//                alreadyExist.invoke()
            }
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
            repo.insert(updated)
            _state.update { it.copy(isEditing = false, selectedPerson = null) }
        }
    }

    fun cancelForm() {
        _state.update { it.copy(isAddingNew = false, isEditing = false, selectedPerson = null) }
    }

    fun reset() {
        _state.update { it.copy(isAddingNew = false, isEditing = false) }
    }
}
