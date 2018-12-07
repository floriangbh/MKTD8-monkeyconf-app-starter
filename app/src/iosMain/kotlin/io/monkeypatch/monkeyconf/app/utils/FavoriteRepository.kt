package io.monkeypatch.monkeyconf.app

actual class FavoriteRepository {
    actual fun saveFavorites(value: List<String>) {}
    actual fun loadFavorites(): List<String> = emptyList()
}