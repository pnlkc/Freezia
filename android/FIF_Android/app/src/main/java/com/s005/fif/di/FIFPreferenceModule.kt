package com.s005.fif.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.s005.fif.di.FIFPreferenceModule.PreferenceKeys.KEY_ACCESS_TOKEN
import com.s005.fif.di.FIFPreferenceModule.PreferenceKeys.KEY_MEMBER_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

const val FIF_PREFERENCE_DATASTORE_NAME = "fif_preference_datastore"
const val ACCESS_TOKEN_NAME = "access_token"
const val MEMBER_ID_NAME = "member_id"
const val AUTHORIZATION = "Authorization"

@Singleton
class FIFPreferenceModule @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FIF_PREFERENCE_DATASTORE_NAME)

    val accessTokenFlow = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            }
        }.map { preferences ->
            preferences[KEY_ACCESS_TOKEN]
        }

    suspend fun setAccessToken(accessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun removeAccessToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_ACCESS_TOKEN)
        }
    }

    val memberIdFlow = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            }
        }.map { preferences ->
            preferences[KEY_MEMBER_ID]
        }

    suspend fun setMemberId(memberId: Int) {
        context.dataStore.edit { preferences ->
            preferences[KEY_MEMBER_ID] = memberId
        }
    }

    suspend fun removeMemberId() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_MEMBER_ID)
        }
    }

    private object PreferenceKeys {
        val KEY_ACCESS_TOKEN = stringPreferencesKey(ACCESS_TOKEN_NAME)
        val KEY_MEMBER_ID = intPreferencesKey(MEMBER_ID_NAME)
    }
}
