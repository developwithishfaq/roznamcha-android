package com.downloader.roznamcha.data.repository

import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.data_source.KhataDao
import com.downloader.roznamcha.data.models.KhataEntryModel
import com.downloader.roznamcha.data.repository.base.BaseRepository

class KhataRepository(
    private val dao: KhataDao,
    private val preferencesHelper: PreferencesHelper
) : BaseRepository<KhataEntryModel>(dao) {

    suspend fun getById(id: String): KhataEntryModel? = dao.getById(id)
    suspend fun deleteByEntryId(entryId: String) = dao.deleteByEntryId(entryId)

    fun getByBusiness(bId: String) = dao.getByBusinessIdFlow(bId)
}
