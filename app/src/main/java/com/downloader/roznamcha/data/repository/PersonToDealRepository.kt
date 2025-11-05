package com.downloader.roznamcha.data.repository

import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.data_source.PersonToDealDao
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.repository.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class PersonToDealRepository(
    private val dao: PersonToDealDao,
    private val preferencesHelper: PreferencesHelper
) : BaseRepository<PersonToDeal>(dao) {

    suspend fun getByKhataNumber(khataNumber: Int): PersonToDeal? =
        dao.getByKhataNumber(khataNumber, preferencesHelper.businessIdFlow.first() ?: "")

    fun getByBusiness(businessId: String): Flow<List<PersonToDeal>> =
        dao.getByBusinessIdFlow(businessId)
}
