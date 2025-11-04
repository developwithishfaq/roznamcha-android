package com.downloader.roznamcha.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "business")
data class Business(
    @PrimaryKey(autoGenerate = false)
    val businessId: String,
    val name: String,
    val description: String,
    val creationTime: Long,
    val updateTime: Long,
)
