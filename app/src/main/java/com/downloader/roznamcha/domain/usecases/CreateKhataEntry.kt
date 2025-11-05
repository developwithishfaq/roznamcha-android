package com.downloader.roznamcha.domain.usecases

import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.models.KhataEntryModel
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.repository.KhataRepository
import kotlinx.coroutines.flow.first

class CreateKhataEntry(
    private val khataRepository: KhataRepository,
    private val preferencesHelper: PreferencesHelper
) {
    suspend operator fun invoke(
        id: String,
        amount: Double,
        incomeForKhataPerson: Boolean,
        person: PersonToDeal,
        rozNamchaId: String,
        actualTime: Long,
    ) {
        val eId = preferencesHelper.employeeIdFlow.first() ?: ""
        val bId = preferencesHelper.businessIdFlow.first() ?: ""
        val prevKhata = khataRepository.getById(id)
        khataRepository.insert(
            KhataEntryModel(
                khataEntryId = id,
                khataNumber = person.khataNumber,
                personName = person.name,
                amount = amount,
                incomeForKhataPerson = incomeForKhataPerson,
                description = "",
                purchaseHistoryId = null,
                khataTime = actualTime,
                creationTime = prevKhata?.creationTime ?: System.currentTimeMillis(),
                updateTime = System.currentTimeMillis(),
                rozNamchaId = rozNamchaId,
                businessId = bId,
                employeeId = eId
            )
        )
    }
}