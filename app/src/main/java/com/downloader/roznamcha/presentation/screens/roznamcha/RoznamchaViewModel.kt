package com.downloader.roznamcha.presentation.screens.roznamcha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.models.RozNamchaPayment
import com.downloader.roznamcha.data.repository.EmployeeRepository
import com.downloader.roznamcha.data.repository.RozNamchaRepository
import com.downloader.roznamcha.domain.extensions.toUiGrouped
import com.downloader.roznamcha.domain.models.RozNamchaGroupedPaymentsUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

data class RozNamchaPaymentUiState(
    val amount: String = "",
    val isMyIncome: Boolean = true,
    val personToDeal: PersonToDeal? = null,
    val isEditMode: Boolean = false,
    val selectedPayment: RozNamchaPayment? = null,
)

class RoznamchaViewModel(
    private val repo: RozNamchaRepository,
    private val preferencesHelper: PreferencesHelper,
    private val employeeRepository: EmployeeRepository
) : ViewModel() {

    // All grouped pages
    private val _groups = MutableStateFlow<List<RozNamchaGroupedPaymentsUi>>(emptyList())
    val groups: StateFlow<List<RozNamchaGroupedPaymentsUi>> = _groups.asStateFlow()

    // current index into groups (0 = newest)
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    // Expose current group (nullable if no data)
    private val _currentGroup = MutableStateFlow<RozNamchaGroupedPaymentsUi?>(null)
    val currentGroup: StateFlow<RozNamchaGroupedPaymentsUi?> = _currentGroup.asStateFlow()


    init {
        viewModelScope.launch {
            repo.getAllFlow().map { it.toUiGrouped() }.collect { grouped ->
                _groups.value = grouped
                if (grouped.isEmpty()) {
                    _currentIndex.value = 0
                    _currentGroup.value = null
                } else {
                    // if current index is out of range, clamp to 0 (latest)
                    val index =
                        _currentIndex.value.coerceAtMost(grouped.lastIndex).coerceAtLeast(0)
                    _currentIndex.value = index
                    _currentGroup.value = grouped.getOrNull(index)
                }
            }
        }
    }

    fun next() {
        val size = _groups.value.size
        if (size == 0) return
        // next = +1 (older)
        val nextIndex = (_currentIndex.value + 1).coerceAtMost(size - 1)
        if (nextIndex != _currentIndex.value) {
            _currentIndex.value = nextIndex
            _currentGroup.value = _groups.value.getOrNull(nextIndex)
        }
    }

    fun previous() {
        val size = _groups.value.size
        if (size == 0) return
        // previous = -1 (newer)
        val prevIndex = (_currentIndex.value - 1).coerceAtLeast(0)
        if (prevIndex != _currentIndex.value) {
            _currentIndex.value = prevIndex
            _currentGroup.value = _groups.value.getOrNull(prevIndex)
        }
    }

    // optional helper to jump to a specific date index
    fun jumpTo(index: Int) {
        val clamped = index.coerceIn(0, _groups.value.lastIndex.coerceAtLeast(0))
        _currentIndex.value = clamped
        _currentGroup.value = _groups.value.getOrNull(clamped)
    }

    private val _state = MutableStateFlow(RozNamchaPaymentUiState())
    val state = _state.asStateFlow()

    fun startAddMode(personKhataNumber: Int, personToDeal: PersonToDeal) {
//        _state.value = RozNamchaPaymentUiState(
//            personKhataNumber = personKhataNumber,
//            personToDeal = personToDeal
//        )
    }

    fun startEditMode(payment: RozNamchaPayment) {
//        _state.value = RozNamchaPaymentUiState(
//            amount = payment.amount.toString(),
//            isMyIncome = payment.isMyIncome,
//            personKhataNumber = payment.personKhataNumber,
//            personName = payment.personName,
//            khataRefId = payment.khataRefId,
//            isEditMode = true,
//            selectedPayment = payment
//        )
    }

    fun updateAmount(amount: String) {
        _state.update { it.copy(amount = amount) }
    }

    fun toggleIncome(isIncome: Boolean) {
        _state.update { it.copy(isMyIncome = isIncome) }
    }

    fun saveOrUpdate(
        onDone: () -> Unit
    ) {
        val current = _state.value
        viewModelScope.launch {
            val businessId = preferencesHelper.businessIdFlow.first() ?: ""
            val employeeName = preferencesHelper.employeeIdFlow.first() ?: ""

            if (current.isEditMode && current.selectedPayment != null) {
                val updated = current.selectedPayment.copy(
                    amount = current.amount.toDoubleOrNull() ?: 0.0,
                    isMyIncome = current.isMyIncome,
                    updateTime = System.currentTimeMillis()
                )
                repo.update(updated)
            } else {
                val new = RozNamchaPayment(
                    id = UUID.randomUUID().toString(),
                    amount = current.amount.toDoubleOrNull() ?: 0.0,
                    isMyIncome = current.isMyIncome,
                    actualTime = System.currentTimeMillis(),
                    creationTime = System.currentTimeMillis(),
                    updateTime = System.currentTimeMillis(),
                    businessId = businessId,
                    addedByEmployee = employeeName,
                    personKhataNumber = current.personToDeal?.khataNumber ?: 0,
                    personName = current.personToDeal?.name ?: "",
                    khataRefId = ""
                )
                repo.insert(new)
            }
            onDone()
        }
    }

    fun reset() {
        _state.value = RozNamchaPaymentUiState()
    }

    fun selectPerson(person: com.downloader.roznamcha.data.models.PersonToDeal?) {
        _state.update {
            it.copy(
                personToDeal = person
            )
        }
    }

}
