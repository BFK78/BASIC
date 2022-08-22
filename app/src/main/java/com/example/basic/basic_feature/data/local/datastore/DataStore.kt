package com.example.basic.basic_feature.data.local.datastore

import android.content.Context

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.basic.core.utils.Constants.SHOWN
import com.example.basic.core.utils.Constants.SHOWN_VALUE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(SHOWN)
val SHOWN_REAL_VALUE = booleanPreferencesKey(SHOWN_VALUE)

suspend fun addShown(context: Context) {
    context.dataStore.edit { settings ->
        settings[SHOWN_REAL_VALUE] = true
    }
}

fun readShown(context: Context): Flow<Boolean> {
    return context.dataStore.data
        .map {
            it[SHOWN_REAL_VALUE] ?: false
        }
}