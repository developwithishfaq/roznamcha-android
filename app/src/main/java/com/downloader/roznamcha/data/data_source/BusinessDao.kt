package com.downloader.roznamcha.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.downloader.roznamcha.data.models.Business

@Dao
interface BusinessDao : BaseDao<Business> {

    @Query("SELECT * FROM business WHERE businessId = :id")
    suspend fun getBusinessById(id: String): Business?

    @Query("SELECT * FROM business")
    suspend fun getAll(): List<Business>

}
