package com.downloader.roznamcha.data.repository

import com.downloader.roznamcha.data.data_source.RozNamchaPaymentDao
import com.downloader.roznamcha.data.models.RozNamchaPayment
import com.downloader.roznamcha.data.repository.base.BaseRepository

class RozNamchaRepository(
    private val dao: RozNamchaPaymentDao
) : BaseRepository<RozNamchaPayment>(dao) {

    suspend fun getRozNamchaPayment(id: String): RozNamchaPayment? = dao.getRozNamchaPayment(id)

    suspend fun getAll() = dao.getAll()
    suspend fun getAllFlow() = dao.getAllFlow()

}
