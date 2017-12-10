package com.jaloveast1k.thisorthat.repository.data

import android.content.Context
import android.content.SharedPreferences
import com.jaloveast1k.thisorthat.repository.data.Consts.General.PREFS_NAME

class PreferencesHelper(val context: Context) {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    inline fun edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = prefs.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * Puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
     */
    fun setValue(key: String, value: Any?) {
        when (value) {
            is String? -> edit({ it.putString(key, value) })
            is Int -> edit({ it.putInt(key, value) })
            is Boolean -> edit({ it.putBoolean(key, value) })
            is Float -> edit({ it.putFloat(key, value) })
            is Long -> edit({ it.putLong(key, value) })
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    /**
     * Finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    inline fun <reified T : Any> getValue(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> prefs.getString(key, defaultValue as? String) as T?
            Int::class -> prefs.getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> prefs.getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> prefs.getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> prefs.getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }
}