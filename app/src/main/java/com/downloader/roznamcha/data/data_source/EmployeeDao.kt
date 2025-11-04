package com.downloader.roznamcha.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.downloader.roznamcha.data.models.EmployeeModel

@Dao
interface EmployeeDao : BaseDao<EmployeeModel> {

    @Query("SELECT * FROM employee WHERE employeeId = :id")
    suspend fun getEmployeeById(id: String): EmployeeModel?

    @Query("SELECT * FROM employee WHERE businessId = :businessId")
    suspend fun getEmployeesByBusiness(businessId: String): List<EmployeeModel>
}
