package com.downloader.roznamcha.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "khata_entries")
data class KhataEntryModel(
    @PrimaryKey(autoGenerate = false)
    val khataEntryId: String,
    val khataNumber: Int,
    val personName: String,
    val amount: Double,
    val income: Boolean,
    val description: String,
    val purchaseHistoryId: String?,
    val khataTime: Long,
    val creationTime: Long,
    val updateTime: Long,
    val rozNamchaId: String?,
    val businessId: String,
    val employeeId: String,
    val canEdited: Boolean,
)