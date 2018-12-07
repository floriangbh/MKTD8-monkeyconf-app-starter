package io.monkeypatch.monkeyconf.app

import platform.Foundation.NSUserDefaults

actual class FavoriteRepository(val defaults: NSUserDefaults) {
    actual constructor() : this(NSUserDefaults.standardUserDefaults)

    actual fun saveFavorites(value: List<String>) {
        defaults.setObject(value.joinToString(","), "favorites")
        defaults.synchronize()
    }
    actual fun loadFavorites(): List<String> =
        (defaults.objectForKey("favorites") as? String?)?.split(",")?.toList() ?: ArrayList()
}
