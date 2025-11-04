package com.downloader.roznamcha.data.data_source

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * Generic BaseDao that handles all common CRUD operations.
 * Every DAO can extend this to inherit standard operations.
 */
interface BaseDao<T> {

    //region --- Room Internal Operations ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInternal(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllInternal(entities: List<T>)

    @Update
    suspend fun updateInternal(entity: T)

    @Update
    suspend fun updateAllInternal(entities: List<T>)

    @Delete
    suspend fun deleteInternal(entity: T)

    @Delete
    suspend fun deleteAllInternal(entities: List<T>)


    suspend fun insert(entity: T) {
        beforeInsert(entity)
        insertInternal(entity)
        afterInsert(entity)
    }

    suspend fun insertAll(entities: List<T>) {
        beforeInsertAll(entities)
        insertAllInternal(entities)
        afterInsertAll(entities)
    }

    suspend fun update(entity: T) {
        beforeUpdate(entity)
        updateInternal(entity)
        afterUpdate(entity)
    }

    suspend fun updateAll(entities: List<T>) {
        beforeUpdateAll(entities)
        updateAllInternal(entities)
        afterUpdateAll(entities)
    }

    suspend fun delete(entity: T) {
        beforeDelete(entity)
        deleteInternal(entity)
        afterDelete(entity)
    }

    suspend fun deleteAll(entities: List<T>) {
        beforeDeleteAll(entities)
        deleteAllInternal(entities)
        afterDeleteAll(entities)
    }
    //endregion

    //region --- Optional Interception Hooks ---
    suspend fun beforeInsert(entity: T) {}
    suspend fun afterInsert(entity: T) {}

    suspend fun beforeInsertAll(entities: List<T>) {}
    suspend fun afterInsertAll(entities: List<T>) {}

    suspend fun beforeUpdate(entity: T) {}
    suspend fun afterUpdate(entity: T) {}

    suspend fun beforeUpdateAll(entities: List<T>) {}
    suspend fun afterUpdateAll(entities: List<T>) {}

    suspend fun beforeDelete(entity: T) {}
    suspend fun afterDelete(entity: T) {}

    suspend fun beforeDeleteAll(entities: List<T>) {}
    suspend fun afterDeleteAll(entities: List<T>) {}
    //endregion
}
