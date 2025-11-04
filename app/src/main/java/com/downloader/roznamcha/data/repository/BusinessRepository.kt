package com.downloader.roznamcha.data.repository

import com.downloader.roznamcha.data.data_source.BusinessDao
import com.downloader.roznamcha.data.models.Business
import com.downloader.roznamcha.data.repository.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BusinessRepository(
    private val dao: BusinessDao
) : BaseRepository<Business>(dao) {

    suspend fun getBusinessById(id: String): Business? = dao.getBusinessById(id)

    fun getAllBusinesses(): Flow<List<Business>> = flow {
        emit(dao.getAll())
    }
}
