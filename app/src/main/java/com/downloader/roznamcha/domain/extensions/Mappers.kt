package com.downloader.roznamcha.domain.extensions

import com.downloader.roznamcha.data.models.RozNamchaPayment
import com.downloader.roznamcha.domain.models.RozNamchaGroupedPaymentsUi
import com.downloader.roznamcha.domain.models.RozNamchaPaymentUi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun List<RozNamchaPayment>.toUiGrouped(): List<RozNamchaGroupedPaymentsUi> {
    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    val mapped = this.map { entity ->
        val date = Date(entity.actualTime)
        RozNamchaPaymentUi(
            id = entity.id,
            amount = entity.amount,
            isMyIncome = entity.isMyIncome,
            formattedDate = dateFormatter.format(date),
            timeMillis = entity.actualTime,
            addedByEmployee = entity.addedByEmployee
        )
    }

    return mapped
        .groupBy { it.formattedDate }
        .map { (date, payments) ->
            RozNamchaGroupedPaymentsUi(
                date = date,
                payments = payments,
                dateMillis = payments[0].timeMillis
            )
        }
        .sortedByDescending { group ->
            group.dateMillis
        }
}
