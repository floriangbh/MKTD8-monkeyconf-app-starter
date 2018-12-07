package io.monkeypatch.utils

import platform.Foundation.NSUserDefaults

actual class FavoriteRepository(
    private val defaults: NSUserDefaults
) {
    actual constructor() : this(NSUserDefaults.standardUserDefaults)

    override fun saveFavorites(value: List<String>) {
        defaults.setObject(value.joinToString(","), "favorites")
    }
    override fun loadString(): List<String> =
        (defaults.objectForKey("favorites") as? String?)?.split(",").toList() ?: emptyList<String>()
}