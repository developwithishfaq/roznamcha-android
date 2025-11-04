package com.downloader.roznamcha.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_to_deal")
data class PersonToDeal(
    @PrimaryKey(autoGenerate = false)
    val personId: String,
    val khataNumber: Int,
    val name: String,
    val role: String,
    val description: String,
    val businessId: String,
    val creationTime: Long,
    val updateTime: Long,
)
