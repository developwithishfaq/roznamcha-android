package com.downloader.roznamcha.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RozNamchaPayment")
data class RozNamchaPayment(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val amount: Double,
    val isMyIncome: Boolean,
    val actualTime: Long,
    val creationTime: Long,
    val updateTime: Long,
    val businessId: String,
    val addedByEmployee: String,
    val personKhataNumber: Int,
    val personName: String,
    val khataRefId: String,
)