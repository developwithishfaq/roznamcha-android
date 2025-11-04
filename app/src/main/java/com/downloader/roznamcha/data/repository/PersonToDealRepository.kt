package com.downloader.roznamcha.data.repository

import com.downloader.roznamcha.data.data_source.PersonToDealDao
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.repository.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PersonToDealRepository(
    private val dao: PersonToDealDao
) : BaseRepository<PersonToDeal>(dao) {

    suspend fun getByKhataNumber(khataNumber: Int): PersonToDeal? =
        dao.getByKhataNumber(khataNumber)

    fun getByBusiness(businessId: String): Flow<List<PersonToDeal>> = flow {
        emit(dao.getByBusinessId(businessId))
    }
}
