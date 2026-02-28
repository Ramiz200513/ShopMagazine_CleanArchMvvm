package com.example.shopmagazine.data.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
private val Context.dataStore by preferencesDataStore("auth_prefs")
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val ACCESS_KEY = stringPreferencesKey("access_token")
    @Volatile
    private var cachedToken: String? = null
    val accessToken: Flow<String?> = context.dataStore.data.map {
        it[ACCESS_KEY]
    }
    fun getToken(): String? = cachedToken
    suspend fun init() {
        cachedToken = context.dataStore.data.first()[ACCESS_KEY]
    }
    suspend fun saveToken(access: String) {
        cachedToken = access
        context.dataStore.edit {
            it[ACCESS_KEY] = access
        }
    }
    suspend fun clearToken() {
        cachedToken = null
        context.dataStore.edit {
            it.clear()
        }
    }
}