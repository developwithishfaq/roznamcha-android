package com.downloader.roznamcha.presentation.screens.roznamcha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.downloader.roznamcha.data.models.RozNamchaPayment
import com.downloader.roznamcha.data.repository.RozNamchaRepository
import com.downloader.roznamcha.domain.extensions.toUiGrouped
import com.downloader.roznamcha.domain.models.RozNamchaGroupedPaymentsUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RoznamchaViewModel(
    private val repository: RozNamchaRepository
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
            repository.getAllFlow().map { it.toUiGrouped() }.collect { grouped ->
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
}
