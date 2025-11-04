package com.downloader.roznamcha.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.downloader.roznamcha.data.data_source.BusinessDao
import com.downloader.roznamcha.data.data_source.EmployeeDao
import com.downloader.roznamcha.data.data_source.KhataDao
import com.downloader.roznamcha.data.data_source.PersonToDealDao
import com.downloader.roznamcha.data.data_source.PurchaseHistoryDao
import com.downloader.roznamcha.data.data_source.RozNamchaPaymentDao
import com.downloader.roznamcha.data.models.Business
import com.downloader.roznamcha.data.models.EmployeeModel
import com.downloader.roznamcha.data.models.KhataEntryModel
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.models.PurchaseHistory
import com.downloader.roznamcha.data.models.RozNamchaPayment
import java.util.concurrent.Executors

@Database(
    entities = [
        Business::class,
        EmployeeModel::class,
        KhataEntryModel::class,
        PersonToDeal::class,
        PurchaseHistory::class,
        RozNamchaPayment::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun businessDao(): BusinessDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun rozNamchaPaymentDao(): RozNamchaPaymentDao
    abstract fun khataDao(): KhataDao
    abstract fun personToDealDao(): PersonToDealDao
    abstract fun purchaseHistoryDao(): PurchaseHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "roznamcha_db"
                ).setQueryCallback(
                    RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
                        Log.d("RoomQuery", "SQL: $sqlQuery | Args: $bindArgs")
                    },
                    Executors.newSingleThreadExecutor()
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
