package com.downloader.roznamcha.domain.models

data class RozNamchaPaymentUi(
    val id: String,
    val amount: Double,
    val isMyIncome: Boolean,
    val formattedDate: String,   // "30 Jan 2025"
    val timeMillis: Long,
    val addedByEmployee: String,
)

data class RozNamchaGroupedPaymentsUi(
    val date: String,
    val dateMillis: Long, // representative millis for sorting/paging
    val payments: List<RozNamchaPaymentUi>
)
