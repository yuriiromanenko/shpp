package ua.shpp.yurom.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val dataStore: DataStore<Preferences>) {

    // DefaultPreferences

    companion object {
        private val EMAIL_KEY = stringPreferencesKey("email_key")
        private val PASS_KEY = stringPreferencesKey("pass_key")
        private val NAME_KEY = stringPreferencesKey("name_key")
        private val REMEMBER = booleanPreferencesKey("boolean_checked_me")
        const val DEFAULT_EMAIL = "aa.bb@cc.dd"
        const val DEFAULT_PASS = "Aa123456"
        const val DEFAULT_REMEMBER_ME_CHECK = false
    }

    suspend fun saveEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
        }
    }

    suspend fun saveCheckBox(cheked: Boolean) {
        dataStore.edit { preferences ->
            preferences[REMEMBER] = cheked
        }
    }

    val checkFlow: Flow<Boolean?> = dataStore.data
        .map { preferences ->
            preferences[REMEMBER] ?: DEFAULT_REMEMBER_ME_CHECK
        }

    suspend fun savePass(pass: String) {
        dataStore.edit { preferences ->
            preferences[PASS_KEY] = pass
        }
    }

    suspend fun saveName(name: String) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }

    val emailFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[EMAIL_KEY] ?: DEFAULT_EMAIL
        }

    val passFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[PASS_KEY] ?: DEFAULT_PASS
        }

    val nameFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[NAME_KEY]
        }
}
