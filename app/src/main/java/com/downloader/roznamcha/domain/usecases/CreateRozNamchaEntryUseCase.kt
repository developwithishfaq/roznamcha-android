package com.downloader.roznamcha.domain.usecases

import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.models.KhataEntryModel
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.models.RozNamchaPayment
import com.downloader.roznamcha.data.repository.KhataRepository
import com.downloader.roznamcha.data.repository.RozNamchaRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import java.util.UUID

class CreateRozNamchaEntryUseCase(
    private val rozNamchaRepository: RozNamchaRepository,
    private val khataRepository: KhataRepository,
    private val preferencesHelper: PreferencesHelper
) {
    suspend operator fun invoke(
        amount: Double,
        person: PersonToDeal,
        isIncome: Boolean
    ) {
        val bId = preferencesHelper.businessIdFlow.first() ?: ""
        val eId = preferencesHelper.employeeIdFlow.first() ?: ""

        val rozNamchaId = UUID.randomUUID().toString()
        delay(20)
        val khataId = UUID.randomUUID().toString()

        rozNamchaRepository.insert(
            RozNamchaPayment(
                id = rozNamchaId,
                amount = amount,
                isMyIncome = isIncome,
                actualTime = System.currentTimeMillis(),
                addedByEmployee = eId,
                businessId = bId,
                creationTime = System.currentTimeMillis(),
                updateTime = System.currentTimeMillis(),
                personName = person.name,
                personKhataNumber = person.khataNumber,
                khataRefId = khataId
            )
        )
        khataRepository.insert(
            KhataEntryModel(
                khataEntryId = khataId,
                khataNumber = person.khataNumber,
                personName = person.name,
                amount = amount,
                incomeForKhataPerson = isIncome.not(),
                description = "Roz Namcha Payment",
                purchaseHistoryId = null,
                khataTime = System.currentTimeMillis(),
                creationTime = System.currentTimeMillis(),
                updateTime = System.currentTimeMillis(),
                rozNamchaId = rozNamchaId,
                businessId = bId,
                employeeId = eId
            )
        )

    }
}