package com.s005.fif.di


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.s005.fif.di.FIFPreferenceModule.PreferenceKeys.KEY_ACCESS_TOKEN
import com.s005.fif.di.FIFPreferenceModule.PreferenceKeys.KEY_RECIPE_DATA
import com.s005.fif.di.FIFPreferenceModule.PreferenceKeys.KEY_TIMER_LIST
import com.s005.fif.fcm.dto.RecipeData
import com.s005.fif.timer.entity.TimerInfoEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

const val FIF_PREFERENCE_DATASTORE_NAME = "fif_preference_datastore"
const val ACCESS_TOKEN_NAME = "access_token"
const val RECIPE_DATA_NAME = "recipe_data"
const val TIMER_LIST_NAME = "timer_list"
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

    val recipeDataFlow = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            }
        }.map { preferences ->
            preferences[KEY_RECIPE_DATA]?.let { Json.decodeFromString<RecipeData>(it) }
        }

    suspend fun setRecipeData(recipeData: RecipeData) {
        val recipeDataToJson = Json.encodeToString(recipeData)

        context.dataStore.edit { preferences ->
            preferences[KEY_RECIPE_DATA] = recipeDataToJson
        }
    }

    suspend fun removeRecipeData() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_RECIPE_DATA)
        }
    }

    val timerListFlow = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            }
        }.map { preferences ->
            preferences[KEY_TIMER_LIST]?.let { Json.decodeFromString<List<TimerInfoEntity>>(it) }
        }

    suspend fun setTimerList(timerList: List<TimerInfoEntity>) {
        val recipeDataToJson = Json.encodeToString(timerList)

        context.dataStore.edit { preferences ->
            preferences[KEY_TIMER_LIST] = recipeDataToJson
        }
    }

    suspend fun removeTimerList() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_TIMER_LIST)
        }
    }

    private object PreferenceKeys {
        val KEY_ACCESS_TOKEN = stringPreferencesKey(ACCESS_TOKEN_NAME)
        val KEY_RECIPE_DATA = stringPreferencesKey(RECIPE_DATA_NAME)
        val KEY_TIMER_LIST = stringPreferencesKey(TIMER_LIST_NAME)
    }
}