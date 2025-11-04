package com.downloader.roznamcha.data.repository.base

import com.downloader.roznamcha.data.data_source.BaseDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseRepository<T>(
    private val dao: BaseDao<T>
) {

    open suspend fun insert(entity: T) = dao.insert(entity)

    open suspend fun update(entity: T) = dao.update(entity)

    open suspend fun delete(entity: T) = dao.delete(entity)

    /**
     * This returns all items via a Flow if you override it in child repositories.
     * The base version emits nothing by default.
     */
    open fun getAllFlow(): Flow<List<T>> = flow { emit(emptyList()) }
}
