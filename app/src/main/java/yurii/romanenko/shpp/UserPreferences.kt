package yurii.romanenko.shpp
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val dataStore: DataStore<Preferences>) {

    // DefaultPreferences
    private  val DEFAULT_EMAIL = "yuri.romanenko@mail.ua"
    private  val DEFAULT_PASS = "KotlinMyLove"

    companion object {
        private val EMAIL_KEY = stringPreferencesKey("email_key")
        private val PASS_KEY = stringPreferencesKey("pass_key")
    }

    suspend fun saveEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
        }
    }

    suspend fun savePass( pass: String) {
        dataStore.edit { preferences ->
            preferences[PASS_KEY] = pass
        }
    }

    val emailFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[EMAIL_KEY] ?: DEFAULT_EMAIL
        }

    val passFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[PASS_KEY]  ?: DEFAULT_PASS
        }
}
