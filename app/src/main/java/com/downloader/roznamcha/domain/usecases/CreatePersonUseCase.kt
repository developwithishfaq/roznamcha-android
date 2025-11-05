package com.downloader.roznamcha.domain.usecases

import android.content.Context
import android.widget.Toast
import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.models.PersonToDeal
import com.downloader.roznamcha.data.repository.PersonToDealRepository
import kotlinx.coroutines.flow.first
import java.util.UUID

class CreatePersonUseCase(
    private val context: Context,
    private val personToDealRepository: PersonToDealRepository,
    private val preferencesHelper: PreferencesHelper
) {
    suspend operator fun invoke(name: String, khataNumber: Int): Boolean {
        val person = personToDealRepository.getByKhataNumber(khataNumber)
        if (person != null) {
            Toast.makeText(context, "This is ${person.name} khata number", Toast.LENGTH_SHORT).show()
            return false
        }
        val businessId = preferencesHelper.businessIdFlow.first() ?: ""
        val newPerson = PersonToDeal(
            personId = UUID.randomUUID().toString(),
            khataNumber = khataNumber,
            name = name,
            role = "",
            description = "",
            businessId = businessId,
            creationTime = System.currentTimeMillis(),
            updateTime = System.currentTimeMillis()
        )
        personToDealRepository.insert(newPerson)
        return true
    }


}