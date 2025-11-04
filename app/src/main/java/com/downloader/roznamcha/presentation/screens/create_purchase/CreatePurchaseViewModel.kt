package com.downloader.roznamcha.presentation.screens.create_purchase

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.downloader.roznamcha.data.models.PurchaseHistory
import com.downloader.roznamcha.data.repository.PurchaseHistoryRepository
import kotlinx.coroutines.launch
import java.util.UUID

class CreatePurchaseViewModel(
    private val repo: PurchaseHistoryRepository
) : ViewModel() {

    var dealerName by mutableStateOf("")
    var dealerKhata by mutableStateOf("")
    var driverName by mutableStateOf("")
    var driverKhata by mutableStateOf("")
    var itemWeight by mutableStateOf("")
    var perKgPrice by mutableStateOf("")
    var perKgDriverWage by mutableStateOf("")

    var showDatePicker by mutableStateOf(false)
    var purchaseTime by mutableStateOf(System.currentTimeMillis())

    fun createPurchase(businessId: String) {
        val purchase = PurchaseHistory(
            purchaseId = UUID.randomUUID().toString(),
            purchaseTime = purchaseTime,
            creationTime = System.currentTimeMillis(),
            updateTime = System.currentTimeMillis(),
            dealerKhataNumber = dealerKhata.toIntOrNull() ?: 0,
            dealerName = dealerName,
            driverKhataNumber = driverKhata.toIntOrNull() ?: 0,
            driverName = driverName,
            itemWeight = itemWeight.toFloatOrNull() ?: 0f,
            perKgPrice = perKgPrice.toFloatOrNull() ?: 0f,
            perKgDriverWage = perKgDriverWage.toFloatOrNull() ?: 0f,
            businessId = businessId
        )
        viewModelScope.launch {
            repo.insert(purchase)
        }
    }
}
