package com.demo.guitarmusicapp.data

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.demo.guitarmusicapp.app.MyApplication
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ApplicationData {

    companion object {
        val DATASTORE_PREFERENCE_NAME = "guitarmusicapp"
        val mDataStorePre = MyApplication.getMyApplication().createDataStore(ApplicationData.DATASTORE_PREFERENCE_NAME)

        suspend fun savePreInfo(name: String, value: String) {
            var preKey = preferencesKey<String>(name)
            mDataStorePre.edit { mutablePreferences ->
                mutablePreferences[preKey] = value
            }
        }

        suspend fun readPreInfo(name: String): String {
            var preKey = preferencesKey<String>(name)
            var value = mDataStorePre.data.map { preferences ->
                preferences[preKey] ?: ""
            }
            return value.first()
        }
    }

}