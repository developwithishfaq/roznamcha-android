package com.downloader.roznamcha.presentation.screens.purchases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.domain.usecases.CreatePurchaseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID


data class PurchaseDetailUiState(
    val weight: String = "",
    val perKgWeightPrice: String = "",
    val perKgDriverWage: String = "",
    val driver: PersonToDeal? = null,
    val dealer: PersonToDeal? = null,
    val selectedPurchaseId: String? = null,
    val paymentDate: Long = System.currentTimeMillis()
)

class PurchasesViewModel(
    private val createPurchaseUseCase: CreatePurchaseUseCase
) : ViewModel() {

    private val _detailState = MutableStateFlow(PurchaseDetailUiState())
    val state = _detailState.asStateFlow()


    fun saveOrUpdate(onDone: () -> Unit) {
        val data = state.value
        viewModelScope.launch {
            data.driver?.let { driver ->
                data.dealer?.let { dealer ->
                    createPurchaseUseCase.invoke(
                        weight = data.weight.toDoubleOrNull() ?: 0.0,
                        perKgPrice = data.perKgWeightPrice.toDoubleOrNull() ?: 0.0,
                        perKgDriverWage = data.perKgDriverWage.toDoubleOrNull() ?: 0.0,
                        actualTime = data.paymentDate,
                        dealer = dealer,
                        driver = driver,
                        id = data.selectedPurchaseId ?: UUID.randomUUID().toString()
                    )
                    onDone()
                }
            }
        }
    }

    fun updateState(state: PurchaseDetailUiState) {
        _detailState.value = state
    }

}