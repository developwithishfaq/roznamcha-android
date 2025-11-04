package com.downloader.roznamcha.core.prefs

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

object PreferencesKeys {
    val ACTIVE_EMPLOYEE_ID = stringPreferencesKey("employee_id")
    val BUSINESS_ID = stringPreferencesKey("business_id")
    val BUSINESS_NAME = stringPreferencesKey("business_name")
    val LAST_LOGIN_TIME = longPreferencesKey("last_login_time")
}
