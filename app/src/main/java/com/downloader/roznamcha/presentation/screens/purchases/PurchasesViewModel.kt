package com.downloader.roznamcha.presentation.screens.purchases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.models.PurchaseHistory
import com.downloader.roznamcha.data.repository.PurchaseHistoryRepository
import com.downloader.roznamcha.domain.usecases.CreatePurchaseUseCase
import com.downloader.roznamcha.domain.usecases.GetPersonByKhata
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
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
    private val createPurchaseUseCase: CreatePurchaseUseCase,
    private val purchaseHistoryRepository: PurchaseHistoryRepository,
    private val preferencesHelper: PreferencesHelper,
    private val getPersonByKhata: GetPersonByKhata
) : ViewModel() {

    private val _history = MutableStateFlow<List<PurchaseHistory>>(emptyList())
    val history = _history.asStateFlow()

    private val _detailState = MutableStateFlow(PurchaseDetailUiState())
    val state = _detailState.asStateFlow()

    init {
        viewModelScope.launch {
            val bId = preferencesHelper.businessIdFlow.first() ?: ""
            purchaseHistoryRepository.getByBusiness(bId).collectLatest { list ->
                _history.update { list }
            }
        }
    }

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

    fun reset() {
        _detailState.update { PurchaseDetailUiState() }
    }

    fun setData(purchase: PurchaseHistory) {
        viewModelScope.launch {
            _detailState.update {
                it.copy(
                    weight = purchase.itemWeight.toString(),
                    perKgWeightPrice = purchase.perKgPrice.toString(),
                    perKgDriverWage = purchase.perKgDriverWage.toString(),
                    driver = getPersonByKhata.invoke(purchase.driverKhataNumber),
                    dealer = getPersonByKhata.invoke(purchase.dealerKhataNumber),
                    selectedPurchaseId = purchase.id,
                    paymentDate = purchase.purchaseTime,
                )
            }
        }
    }


}