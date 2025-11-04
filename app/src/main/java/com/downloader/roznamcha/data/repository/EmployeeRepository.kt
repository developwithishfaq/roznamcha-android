package com.downloader.roznamcha.data.repository

import com.downloader.roznamcha.data.data_source.EmployeeDao
import com.downloader.roznamcha.data.models.EmployeeModel
import com.downloader.roznamcha.data.repository.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EmployeeRepository(
    private val dao: EmployeeDao
) : BaseRepository<EmployeeModel>(dao) {

    suspend fun getEmployeeById(id: String): EmployeeModel? = dao.getEmployeeById(id)

    fun getEmployeesByBusiness(businessId: String): Flow<List<EmployeeModel>> = flow {
        emit(dao.getEmployeesByBusiness(businessId))
    }
}
