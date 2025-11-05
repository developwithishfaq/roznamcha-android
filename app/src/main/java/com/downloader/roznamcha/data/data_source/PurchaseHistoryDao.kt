package com.downloader.roznamcha.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.downloader.roznamcha.data.models.PurchaseHistory

@Dao
interface PurchaseHistoryDao : BaseDao<PurchaseHistory> {

    @Query("SELECT * FROM purchase_history WHERE id = :id")
    suspend fun getById(id: String): PurchaseHistory?

    @Query("SELECT * FROM purchase_history WHERE businessId = :businessId")
    suspend fun getByBusinessId(businessId: String): List<PurchaseHistory>

    @Query("SELECT * FROM purchase_history WHERE dealerKhataNumber = :dealerNumber")
    suspend fun getByDealerKhataNumber(dealerNumber: Int): List<PurchaseHistory>

}
