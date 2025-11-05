package com.downloader.roznamcha.domain.usecases

import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.models.RozNamchaPayment
import com.downloader.roznamcha.data.repository.RozNamchaRepository
import kotlinx.coroutines.flow.first
import java.util.UUID

class CreateRozNamchaUseCase(
    private val rozNamchaRepository: RozNamchaRepository,
    private val preferencesHelper: PreferencesHelper,
    private val createKhataEntry: CreateKhataEntry
) {
    suspend operator fun invoke(
        amount: Double,
        income: Boolean,
        actualTime: Long,
        personToDeal: PersonToDeal
    ) {
        val id = UUID.randomUUID().toString()
        val khataId = UUID.randomUUID().toString()
        val bId = preferencesHelper.businessIdFlow.first() ?: ""
        val eId = preferencesHelper.employeeIdFlow.first() ?: ""
        val time = System.currentTimeMillis()

        rozNamchaRepository.update(
            RozNamchaPayment(
                id = id,
                amount = amount,
                isMyIncome = income,
                actualTime = actualTime,
                creationTime = time,
                updateTime = time,
                businessId = bId,
                addedByEmployee = eId,
                personKhataNumber = personToDeal.khataNumber,
                personName = personToDeal.name,
                khataRefId = khataId
            )
        )
        createKhataEntry.invoke(
            id = khataId,
            amount = amount,
            incomeForKhataPerson = income,
            person = personToDeal,
            rozNamchaId = id,
            actualTime = actualTime
        )
    }
}