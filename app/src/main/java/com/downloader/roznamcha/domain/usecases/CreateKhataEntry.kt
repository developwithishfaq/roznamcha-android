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
        income: Boolean,
        person: PersonToDeal,
        rozNamchaId: String? = null,
        purchaseId: String? = null,
        actualTime: Long,
        canEdit: Boolean
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
                income = income,
                description = "",
                purchaseHistoryId = purchaseId,
                khataTime = actualTime,
                creationTime = prevKhata?.creationTime ?: System.currentTimeMillis(),
                updateTime = System.currentTimeMillis(),
                rozNamchaId = rozNamchaId,
                businessId = bId,
                employeeId = eId,
                canEdited = canEdit
            )
        )
    }
}