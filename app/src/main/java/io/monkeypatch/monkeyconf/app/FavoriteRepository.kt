package io.monkeypatch.monkeyconf.app

import android.content.SharedPreferences
import android.preference.PreferenceManager


actual class FavoriteRepository(
    private val sharedPreferences: SharedPreferences
) {
    actual constructor() : this(PreferenceManager.getDefaultSharedPreferences(Application.instance))

    actual  fun saveFavorites(value: List<String>) {
        val editor = sharedPreferences.edit()
        editor.putString("favorites", value.joinToString(","))
        editor.commit()
    }

    actual fun loadFavorites(): List<String> =
        sharedPreferences.getString("favorites", "")?.split(",")?.toList() ?: listOf()
}