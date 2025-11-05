package com.downloader.roznamcha.presentation.screens.roznamcha

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.repository.RozNamchaRepository
import com.downloader.roznamcha.domain.extensions.toUiGrouped
import com.downloader.roznamcha.domain.models.RozNamchaGroupedPaymentsUi
import com.downloader.roznamcha.domain.models.RozNamchaPaymentUi
import com.downloader.roznamcha.domain.usecases.CreateRozNamchaUseCase
import com.downloader.roznamcha.domain.usecases.GetPersonByKhata
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

data class RozNamchaPaymentUiState(
    val amount: String = "",
    val income: Boolean = true,
    val personToDeal: PersonToDeal? = null,
    val selectedPaymentId: String? = null,
    val paymentDate: Long = System.currentTimeMillis()
)

class RoznamchaViewModel(
    private val repo: RozNamchaRepository,
    private val createRozNamchaEntryUseCase: CreateRozNamchaUseCase,
    private val getPersonByKhata: GetPersonByKhata
) : ViewModel() {

    // All grouped pages with balances
    private val _groups = MutableStateFlow<List<RozNamchaGroupedPaymentsUi>>(emptyList())
    val groups: StateFlow<List<RozNamchaGroupedPaymentsUi>> = _groups.asStateFlow()

    // current index into groups (0 = newest)
    private val _currentIndex = MutableStateFlow(-1)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    // Expose current group (nullable if no data)
    private val _currentGroup = MutableStateFlow<RozNamchaGroupedPaymentsUi?>(null)
    val currentGroup: StateFlow<RozNamchaGroupedPaymentsUi?> = _currentGroup.asStateFlow()

    private val TAG = "cvv"

    init {
        viewModelScope.launch {
            repo.getAllFlow().collectLatest { state ->
                val grouped = state.toUiGrouped().reversed()

                // Calculate balances for each group
                val groupsWithBalances = calculateBalances(grouped)

                Log.d(TAG, "Items(${state.size}):${groupsWithBalances.map { it.date }}")
                _groups.value = groupsWithBalances

                if (groupsWithBalances.isEmpty()) {
                    _currentIndex.value = 0
                    _currentGroup.value = null
                } else {
                    // Start from the latest day (last index)
                    val index = groupsWithBalances.size - 1
                    _currentIndex.value = index
                    _currentGroup.value = groupsWithBalances.getOrNull(index)
                }
            }
        }
    }

    /**
     * Calculate opening and closing balances for each group
     * Opening balance = closing balance of previous day
     * Closing balance = opening balance + (income - expense)
     */
    private fun calculateBalances(groups: List<RozNamchaGroupedPaymentsUi>): List<RozNamchaGroupedPaymentsUi> {
        if (groups.isEmpty()) return emptyList()

        var runningBalance = 0.0

        return groups.map { group ->
            val openingBalance = runningBalance

            // Calculate day's transactions
            val dayIncome = group.payments
                .filter { it.income }
                .sumOf { it.amount }

            val dayExpense = group.payments
                .filter { !it.income }
                .sumOf { it.amount }

            val netChange = dayIncome - dayExpense
            val closingBalance = openingBalance + netChange

            // Update running balance for next day
            runningBalance = closingBalance

            group.copy(
                openingBalance = openingBalance,
                closingBalance = closingBalance
            )
        }
    }

    fun next() {
        val size = _groups.value.size
        if (size == 0) return
        val newIndex = currentIndex.value + 1
        Log.d(TAG, "next newIndex(size=$size): $newIndex")
        val group = _groups.value.getOrNull(newIndex)
        if (group != null) {
            _currentIndex.value = newIndex
            _currentGroup.value = group
        }
    }

    fun previous() {
        val size = _groups.value.size
        if (size == 0) return
        val newIndex = currentIndex.value - 1
        Log.d(TAG, "previous newIndex(size=$size): $newIndex")
        val group = _groups.value.getOrNull(newIndex)
        if (group != null) {
            _currentIndex.value = newIndex
            _currentGroup.value = group
        }
    }

    private val _state = MutableStateFlow(RozNamchaPaymentUiState())
    val state = _state.asStateFlow()

    fun updatePaymentDate(timeInMillis: Long) {
        _state.update { it.copy(paymentDate = timeInMillis) }
    }

    fun updateAmount(amount: String) {
        _state.update { it.copy(amount = amount) }
    }

    fun toggleIncome(isIncome: Boolean) {
        _state.update { it.copy(income = isIncome) }
    }

    fun saveOrUpdate(onDone: () -> Unit) {
        val current = _state.value
        viewModelScope.launch {
            current.personToDeal?.let {
                createRozNamchaEntryUseCase.invoke(
                    id = state.value.selectedPaymentId ?: UUID.randomUUID().toString(),
                    amount = current.amount.toDoubleOrNull() ?: 0.0,
                    income = current.income,
                    personToDeal = current.personToDeal,
                    actualTime = current.paymentDate
                )
            }
            onDone()
        }
    }

    fun reset() {
        _state.value = RozNamchaPaymentUiState()
    }

    fun selectPerson(person: PersonToDeal?) {
        _state.update {
            it.copy(personToDeal = person)
        }
    }

    fun onPaymentClicked(payment: RozNamchaPaymentUi) {
        viewModelScope.launch {
            val personToDeal = getPersonByKhata.invoke(payment.khataNumber)
            _state.update {
                it.copy(
                    selectedPaymentId = payment.id,
                    amount = payment.amount.toString(),
                    income = payment.income,
                    personToDeal = personToDeal,
                    paymentDate = payment.timeMillis
                )
            }
        }
    }
}