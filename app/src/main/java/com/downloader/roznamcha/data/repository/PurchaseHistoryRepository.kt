package com.downloader.roznamcha.data.repository

import com.downloader.roznamcha.data.data_source.PurchaseHistoryDao
import com.downloader.roznamcha.data.models.PurchaseHistory
import com.downloader.roznamcha.data.repository.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PurchaseHistoryRepository(
    private val dao: PurchaseHistoryDao
) : BaseRepository<PurchaseHistory>(dao) {

    suspend fun getById(id: String): PurchaseHistory? = dao.getById(id)

    fun getByBusiness(businessId: String): Flow<List<PurchaseHistory>> {
        return dao.getByBusinessIdFlow(businessId)
    }

    fun getByDealerKhataNumber(dealerNumber: Int): Flow<List<PurchaseHistory>> = flow {
        emit(dao.getByDealerKhataNumber(dealerNumber))
    }
}
