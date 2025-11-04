package com.downloader.roznamcha.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.downloader.roznamcha.data.models.Business
import com.downloader.roznamcha.data.models.RozNamchaPayment

@Dao
interface RozNamchaPaymentDao : BaseDao<RozNamchaPayment> {

    @Query("SELECT * FROM roznamchapayment WHERE id = :id")
    suspend fun getRozNamchaPayment(id: String): RozNamchaPayment?

    @Query("SELECT * FROM RozNamchaPayment")
    suspend fun getAll(): List<RozNamchaPayment>

}
