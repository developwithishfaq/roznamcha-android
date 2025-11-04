package com.downloader.roznamcha.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.downloader.roznamcha.data.models.PersonToDeal

@Dao
interface PersonToDealDao : BaseDao<PersonToDeal> {

    @Query("SELECT * FROM person_to_deal WHERE khataNumber = :khataNumber")
    suspend fun getByKhataNumber(khataNumber: Int): PersonToDeal?

    @Query("SELECT * FROM person_to_deal WHERE businessId = :businessId")
    suspend fun getByBusinessId(businessId: String): List<PersonToDeal>

}
