package com.downloader.roznamcha.data.repository

import com.downloader.roznamcha.data.data_source.BusinessDao
import com.downloader.roznamcha.data.data_source.RozNamchaPaymentDao
import com.downloader.roznamcha.data.models.Business
import com.downloader.roznamcha.data.models.RozNamchaPayment
import com.downloader.roznamcha.data.repository.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RozNamchaRepository(
    private val dao: RozNamchaPaymentDao
) : BaseRepository<RozNamchaPayment>(dao) {

    suspend fun getRozNamchaPayment(id: String): RozNamchaPayment? = dao.getRozNamchaPayment(id)

    fun getAllBusinesses(): Flow<List<RozNamchaPayment>> = flow {
        emit(dao.getAll())
    }
}
