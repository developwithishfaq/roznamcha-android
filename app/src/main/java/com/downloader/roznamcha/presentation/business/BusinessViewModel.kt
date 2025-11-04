package com.downloader.roznamcha.presentation.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.domain.usecases.CreateBusinessUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BusinessViewModel(
    private val createBusiness: CreateBusinessUseCase,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(BusinessUiState())
    val uiState: StateFlow<BusinessUiState> = _uiState


    suspend fun getBusinessInfo(): String? {
        return preferencesHelper.businessIdFlow.first()
    }

    fun onNameChange(value: String) = _uiState.update { it.copy(name = value) }
    fun onDescriptionChange(value: String) = _uiState.update { it.copy(description = value) }

    fun createBusiness(done: () -> Unit) {
        val state = _uiState.value
        if (state.name.isBlank()) {
            _uiState.update { it.copy(message = "Name cannot be empty") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = null) }
            try {
                createBusiness.invoke(state.name, state.description)
                _uiState.update {
                    it.copy(
                        name = "",
                        description = "",
                        isLoading = false,
                        message = "Business created successfully!"
                    )
                }
                done.invoke()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, message = e.localizedMessage ?: "Error occurred")
                }
            }
        }
    }
}
