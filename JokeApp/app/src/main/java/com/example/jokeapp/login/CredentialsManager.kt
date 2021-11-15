package com.example.jokeapp.login

import android.content.Context
import com.auth0.android.result.Credentials
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


object CredentialsManager {
    private val PREFERENCES_NAME = "auth0"
    private val ACCESS_TOKEN = "access_token"

    private lateinit var editor: SharedPreferences.Editor

    fun saveCredentials(context: Context, credentials: Credentials) {

        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        val sp: SharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        // use the shared preferences and editor as you normally would

        // use the shared preferences and editor as you normally would
        editor = sp.edit()


        editor.putString(ACCESS_TOKEN, credentials.accessToken)
            .apply()
    }

    fun getAccessToken(context: Context): String? {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        val sp: SharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )



        return sp.getString(ACCESS_TOKEN, null)
    }
}