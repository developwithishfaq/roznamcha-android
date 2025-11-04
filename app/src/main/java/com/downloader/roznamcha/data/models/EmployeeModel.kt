package com.downloader.roznamcha.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class EmployeeModel(
    @PrimaryKey(autoGenerate = false)
    val employeeId: String,
    val name: String,
    val email: String,
    val password: String,
    val isOwner: Boolean,
    val role: String,
    val description: String,
    val businessId: String,
    val creationTime: Long,
    val updateTime: Long,
)
