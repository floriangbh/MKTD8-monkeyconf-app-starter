package io.monkeypatch.monkeyconf.app

expect class FavoriteRepository() {
    fun saveFavorites(value: List<String>)
    fun loadFavorites(): List<String>
}