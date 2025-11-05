package com.downloader.roznamcha.domain.usecases

import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.models.RozNamchaPayment
import com.downloader.roznamcha.data.repository.KhataRepository
import com.downloader.roznamcha.data.repository.RozNamchaRepository
import kotlinx.coroutines.flow.first
import java.util.UUID

class CreateRozNamchaUseCase(
    private val rozNamchaRepository: RozNamchaRepository,
    private val preferencesHelper: PreferencesHelper,
    private val khataRepository: KhataRepository,
    private val createKhataEntry: CreateKhataEntry
) {
    suspend operator fun invoke(
        amount: Double,
        income: Boolean,
        actualTime: Long,
        personToDeal: PersonToDeal,
        id: String = UUID.randomUUID().toString()
    ) {

        val prevRozNamcha = rozNamchaRepository.getRozNamchaPayment(id)

        var khataId = UUID.randomUUID().toString()

        if (prevRozNamcha != null) {
            if (personToDeal.khataNumber != prevRozNamcha.personKhataNumber) {
                khataRepository.deleteByEntryId(prevRozNamcha.khataRefId)
            } else {
                khataId = prevRozNamcha.khataRefId
            }
        }

        val bId = preferencesHelper.businessIdFlow.first() ?: ""
        val eId = preferencesHelper.employeeIdFlow.first() ?: ""
        val time = System.currentTimeMillis()

        rozNamchaRepository.insert(
            RozNamchaPayment(
                id = id,
                amount = amount,
                income = income,
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
            income = income.not(),
            person = personToDeal,
            rozNamchaId = id,
            actualTime = actualTime,
            canEdit = false
        )
    }
}