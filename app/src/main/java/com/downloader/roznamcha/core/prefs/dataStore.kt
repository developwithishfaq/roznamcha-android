package com.downloader.roznamcha.core.prefs

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property on Context (only one DataStore per file name)
private val Context.dataStore by preferencesDataStore(name = "app_preferences")

class PreferencesHelper(private val context: Context) {

    // Save business info
    suspend fun setCurrentEmployeeId(employeeId: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.ACTIVE_EMPLOYEE_ID] = employeeId
        }
    }

    suspend fun saveBusinessInfo(businessId: String, businessName: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.BUSINESS_ID] = businessId
            prefs[PreferencesKeys.BUSINESS_NAME] = businessName
        }
    }

    // Read businessId
    val employeeIdFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[PreferencesKeys.ACTIVE_EMPLOYEE_ID]
    }
    val businessIdFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[PreferencesKeys.BUSINESS_ID]
    }

    // Read businessName
    val businessNameFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[PreferencesKeys.BUSINESS_NAME]
    }

    // Save single string
    suspend fun saveString(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { prefs -> prefs[key] = value }
    }

    // Save single long
    suspend fun saveLong(key: Preferences.Key<Long>, value: Long) {
        context.dataStore.edit { prefs -> prefs[key] = value }
    }

    // Generic getter for strings
    fun getStringFlow(key: Preferences.Key<String>): Flow<String?> =
        context.dataStore.data.map { it[key] }

    // Generic getter for longs
    fun getLongFlow(key: Preferences.Key<Long>): Flow<Long?> =
        context.dataStore.data.map { it[key] }

    // Clear all
    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}
