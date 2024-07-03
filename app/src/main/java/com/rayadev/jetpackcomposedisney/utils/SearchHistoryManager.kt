package com.rayadev.jetpackcomposedisney.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SearchHistoryManager {
    private const val PREFS_NAME = "SearchHistory"
    private const val KEY_SEARCH_HISTORY = "search_history"

    fun saveSearch(query: String, context: Context) {
        val prefs = getPrefs(context)
        val currentHistory = prefs.getStringSet(KEY_SEARCH_HISTORY, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        currentHistory.add(query)
        prefs.edit {
            putStringSet(KEY_SEARCH_HISTORY, currentHistory)
        }
    }

    fun getSearchHistory(context: Context): List<String> {
        val prefs = getPrefs(context)
        return prefs.getStringSet(KEY_SEARCH_HISTORY, mutableSetOf())?.toList() ?: emptyList()
    }

    fun clearSearchHistory(context: Context) {
        val prefs = getPrefs(context)
        prefs.edit {
            remove(KEY_SEARCH_HISTORY)
        }
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
}
