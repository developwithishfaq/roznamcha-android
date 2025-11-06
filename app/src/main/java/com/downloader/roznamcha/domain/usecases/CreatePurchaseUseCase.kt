package com.downloader.roznamcha.domain.usecases

import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.models.PurchaseHistory
import com.downloader.roznamcha.data.repository.KhataRepository
import com.downloader.roznamcha.data.repository.PurchaseHistoryRepository
import kotlinx.coroutines.flow.first
import java.util.UUID

class CreatePurchaseUseCase(
    private val purchaseRepo: PurchaseHistoryRepository,
    private val preferencesHelper: PreferencesHelper,
    private val khataRepository: KhataRepository,
    private val createKhataEntry: CreateKhataEntry
) {
    suspend operator fun invoke(
        weight: Double,
        perKgPrice: Double,
        perKgDriverWage: Double,
        actualTime: Long,
        dealer: PersonToDeal,
        driver: PersonToDeal,
        id: String = UUID.randomUUID().toString()
    ) {

        val prevRozNamcha = purchaseRepo.getById(id)

        var dealerKhataId = UUID.randomUUID().toString()
        if (prevRozNamcha != null) {
            if (dealer.khataNumber != prevRozNamcha.dealerKhataNumber) {
                khataRepository.deleteByEntryId(prevRozNamcha.dealerKhataReferenceId)
            } else {
                dealerKhataId = prevRozNamcha.dealerKhataReferenceId
            }
        }
        var driverKhataId = UUID.randomUUID().toString()
        if (prevRozNamcha != null) {
            if (driver.khataNumber != prevRozNamcha.driverKhataNumber) {
                khataRepository.deleteByEntryId(prevRozNamcha.driverKhataReferenceId)
            } else {
                driverKhataId = prevRozNamcha.driverKhataReferenceId
            }
        }

        val bId = preferencesHelper.businessIdFlow.first() ?: ""
        val eId = preferencesHelper.employeeIdFlow.first() ?: ""
        val time = System.currentTimeMillis()

        purchaseRepo.insert(
            PurchaseHistory(
                id = id,
                creationTime = time,
                updateTime = time,
                purchaseTime = actualTime,

                dealerKhataNumber = dealer.khataNumber,
                dealerName = dealer.name,
                dealerKhataReferenceId = dealerKhataId,

                driverKhataNumber = driver.khataNumber,
                driverName = driver.name,
                driverKhataReferenceId = driverKhataId,

                itemWeight = weight,
                perKgPrice = perKgPrice,
                perKgDriverWage = perKgDriverWage,
                businessId = bId,
                employeeId = eId
            )
        )
        val driverAmount = (weight * perKgDriverWage)
        createKhataEntry.invoke(
            id = driverKhataId,
            amount = driverAmount,
            income = false,
            person = driver,
            rozNamchaId = id,
            actualTime = actualTime,
            canEdit = false
        )

        val dealerAmount = weight * perKgPrice

        createKhataEntry.invoke(
            id = dealerKhataId,
            amount = dealerAmount,
            income = false,
            person = dealer,
            purchaseId = id,
            actualTime = actualTime,
            canEdit = false
        )

    }
}