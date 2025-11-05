package com.downloader.roznamcha.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase_history")
data class PurchaseHistory(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val purchaseTime: Long,
    val creationTime: Long,
    val updateTime: Long,

    val dealerKhataNumber: Int,
    val dealerName: String,
    val dealerKhataReferenceId: String,

    val driverKhataNumber: Int,
    val driverName: String,
    val driverKhataReferenceId: String,

    val itemWeight: Double,
    val perKgPrice: Double,
    val perKgDriverWage: Double,
    val businessId: String,
    val employeeId: String,


)
