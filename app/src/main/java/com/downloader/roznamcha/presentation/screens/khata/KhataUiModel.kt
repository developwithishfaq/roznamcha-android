package com.downloader.roznamcha.presentation.screens.khata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.models.KhataEntryModel
import com.downloader.roznamcha.data.repository.KhataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class KhataUiModel(
    val khataNumber: Int,
    val personName: String,
    val totalCredit: Double,
    val totalDebit: Double,
    val balance: Double,
    val entries: List<KhataEntryModel>
)

class KhataViewModel(
    private val repository: KhataRepository,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {

    private val _khatas = MutableStateFlow<List<KhataUiModel>>(emptyList())
    val khatas: StateFlow<List<KhataUiModel>> = _khatas

    init {
        loadKhatas()
    }

    fun loadKhatas() {
        viewModelScope.launch {
            val businessId = preferencesHelper.businessIdFlow.first() ?: ""
            repository.getByBusiness(businessId).collectLatest { entries ->
                val grouped = entries.groupBy { it.khataNumber }
                val khataUiList = grouped.map { (khataNumber, list) ->
                    val first = list.firstOrNull()
                    val totalCredit = list.filter { it.income }.sumOf { it.amount }
                    val totalDebit = list.filter { !it.income }.sumOf { it.amount }
                    val balance = totalCredit - totalDebit

                    KhataUiModel(
                        khataNumber = khataNumber,
                        personName = first?.personName ?: "Unknown",
                        totalCredit = totalCredit,
                        totalDebit = totalDebit,
                        balance = balance,
                        entries = list.sortedByDescending { it.khataTime }
                    )
                }.sortedByDescending { it.balance }
                _khatas.value = khataUiList
            }
        }
    }
}
