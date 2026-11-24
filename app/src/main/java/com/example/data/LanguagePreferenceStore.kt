package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.AppLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.ridePreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
  name = "my_ride_preferences"
)

class LanguagePreferenceStore(private val context: Context) {
  private object Keys {
    val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
  }

  val selectedLanguage: Flow<AppLanguage> =
    context.ridePreferencesDataStore.data.map { prefs ->
      AppLanguage.fromCode(prefs[Keys.SELECTED_LANGUAGE])
    }

  suspend fun saveLanguage(language: AppLanguage) {
    context.ridePreferencesDataStore.edit { prefs ->
      prefs[Keys.SELECTED_LANGUAGE] = language.code
    }
  }
}
