package com.chsteam.agent.setting

import android.content.Context
import com.chsteam.agent.function.FunctionManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Settings(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE)

    companion object {
        const val API = "api"
        lateinit var OpenAI_KEY : String
        var UseGPT4 : Boolean = false
        lateinit var FEATURES : Features
    }

    init {
        OpenAI_KEY = getUserSetting(API, "")
        UseGPT4 = getUserSetting("gpt4", false)
        FEATURES = Features(this)
    }

    fun saveUserSetting(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun saveUserSetting(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun saveUserSetting(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun saveUserSetting(key: String, value: Float) {
        val editor = sharedPreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun saveUserSetting(key: String, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun saveUserSetting(key: String, list: List<String>) {
        val gson = Gson()
        val json = gson.toJson(list)
        saveUserSetting(key, json)
    }

    private fun getUserSetting(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    private fun getUserSetting(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    private fun getUserSetting(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    private fun getUserSetting(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    private fun getUserSetting(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    private fun getUserSetting(key: String): List<String> {
        val gson = Gson()
        val json = getUserSetting(key, "")
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }

    class Features(private val settings: Settings) {
        private val features = HashMap<String, Boolean>()

        fun setFeature(feature: String, isEnabled: Boolean) {
            features[feature] = isEnabled
        }

        fun isFeatureEnabled(feature: String): Boolean {
            return features[feature] ?: false
        }

        fun save() {
            if (features.isNotEmpty()) {
                val gson = Gson()
                val json = gson.toJson(features)
                settings.saveUserSetting("features", json)
            }
        }

        init {
            val json = settings.getUserSetting("features", "")
            if (json.isNotEmpty()) {
                val gson = Gson()
                val type = object : TypeToken<HashMap<String, Boolean>>() {}.type
                val existingFeatures: HashMap<String, Boolean> = gson.fromJson(json, type)
                features.putAll(existingFeatures)
            }

            FunctionManager.getAllFunctions().keys.forEach {
                features.getOrPut(it) { false }
            }
        }
    }
}