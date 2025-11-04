package com.downloader.roznamcha.domain.usecases

import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.data.models.Business
import com.downloader.roznamcha.data.models.EmployeeModel
import com.downloader.roznamcha.data.repository.BusinessRepository
import com.downloader.roznamcha.data.repository.EmployeeRepository
import java.util.UUID

class CreateBusinessUseCase(
    private val repo: BusinessRepository,
    private val employeeRepository: EmployeeRepository,
    private val preferencesHelper: PreferencesHelper
) {
    suspend operator fun invoke(name: String, desc: String) {
        val time = System.currentTimeMillis()
        val id = UUID.randomUUID().toString()
        val business = Business(
            businessId = id,
            name = name,
            description = desc,
            creationTime = time,
            updateTime = time,
        )
        repo.insert(business)
        preferencesHelper.saveBusinessInfo(businessId = id, businessName = name)
        val employeeId = UUID.randomUUID().toString()
        employeeRepository.insert(
            EmployeeModel(
                employeeId = employeeId,
                businessId = id,
                name = "Owner",
                isOwner = true,
                creationTime = time,
                updateTime = time,
                description = "First Employee Of Business",
                role = "Admin",
                email = "admin@gmail.com",
                password = ""
            )
        )
        preferencesHelper.setCurrentEmployeeId(employeeId)
    }
}