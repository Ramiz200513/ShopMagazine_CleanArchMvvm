package com.example.shopmagazine.data.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
private val Context.dataStore by preferencesDataStore("auth_prefs")
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context,
){
    private val ACCESS_KEY = stringPreferencesKey("access_token")
    val accessToken: Flow<String?> = context.dataStore.data.map {
         it[ACCESS_KEY]
    }
    suspend fun saveToken(access:String) {
        context.dataStore.edit {
            it[ACCESS_KEY] = access
        }
    }
    suspend fun clearToken(){
        context.dataStore.edit {
            it.clear()
        }
    }

}