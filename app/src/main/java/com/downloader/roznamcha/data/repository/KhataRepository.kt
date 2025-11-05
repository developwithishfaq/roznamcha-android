package com.downloader.roznamcha.data.repository

import com.downloader.roznamcha.data.data_source.KhataDao
import com.downloader.roznamcha.data.models.KhataEntryModel
import com.downloader.roznamcha.data.repository.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class KhataRepository(
    private val dao: KhataDao
) : BaseRepository<KhataEntryModel>(dao) {

    suspend fun getById(id: String): KhataEntryModel? = dao.getById(id)
    suspend fun deleteByEntryId(entryId: String) = dao.deleteByEntryId(entryId)

    fun getByBusiness(businessId: String): Flow<List<KhataEntryModel>> = flow {
        emit(dao.getByBusinessId(businessId))
    }

    fun getByKhataNumber(khataNumber: Int): Flow<List<KhataEntryModel>> = flow {
        emit(dao.getByKhataNumber(khataNumber))
    }
}
