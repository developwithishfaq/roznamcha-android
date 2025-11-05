package com.downloader.roznamcha.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.downloader.roznamcha.data.models.KhataEntryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface KhataDao : BaseDao<KhataEntryModel> {
    @Query("SELECT * FROM khata_entries WHERE khataEntryId = :id")
    suspend fun getById(id: String): KhataEntryModel?

    @Query("DELETE FROM khata_entries WHERE khataEntryId = :entryId")
    suspend fun deleteByEntryId(entryId: String)

    @Query("SELECT * FROM khata_entries WHERE businessId = :businessId")
    suspend fun getByBusinessId(businessId: String): List<KhataEntryModel>

    @Query("SELECT * FROM khata_entries WHERE businessId = :businessId")
    fun getByBusinessIdFlow(businessId: String): Flow<List<KhataEntryModel>>

    @Query("SELECT * FROM khata_entries WHERE khataNumber = :khataNumber")
    suspend fun getByKhataNumber(khataNumber: Int): List<KhataEntryModel>
}
