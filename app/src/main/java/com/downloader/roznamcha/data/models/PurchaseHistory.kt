package com.downloader.roznamcha.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase_history")
data class PurchaseHistory(
    @PrimaryKey(autoGenerate = false)
    val purchaseId: String,
    val purchaseTime: Long,
    val creationTime: Long,
    val updateTime: Long,

    val dealerKhataNumber: Int,
    val dealerName: String,

    val driverKhataNumber: Int,
    val driverName: String,

    val itemWeight: Float,
    val perKgPrice: Float,
    val perKgDriverWage: Float,
    val businessId: String,

)
