package com.downloader.roznamcha.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.downloader.roznamcha.data.models.PersonToDeal
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonToDealDao : BaseDao<PersonToDeal> {

    @Query("SELECT * FROM person_to_deal WHERE khataNumber = :khataNumber AND businessId = :businessId")
    suspend fun getByKhataNumber(khataNumber: Int, businessId: String): PersonToDeal?

    @Query("SELECT * FROM person_to_deal WHERE businessId = :businessId")
    suspend fun getByBusinessId(businessId: String): List<PersonToDeal>

    @Query("SELECT * FROM person_to_deal WHERE businessId = :businessId ORDER BY creationTime DESC")
    fun getByBusinessIdFlow(businessId: String): Flow<List<PersonToDeal>>

}
