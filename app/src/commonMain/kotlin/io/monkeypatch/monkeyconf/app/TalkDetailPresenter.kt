package io.monkeypatch.monkeyconf.app

import kotlinx.coroutines.launch

class TalkDetailPresenter(view: TalkDetailView) : BasePresenter<TalkDetailView>(view) {

    val repository = Container.conferenceRepository
    val favRepository = Container.favoritesRepository

    lateinit var talkId: String

    fun loadDetails(id: String) {
        talkId = id
        launch {
            try {
                repository.getTalk(id)?.let {
                    view.displayTalk(it, isFavorite())
                }
            } catch (e: Exception) {
                view.displayError(e)
            }
        }
    }

    private fun isFavorite() = talkId in favRepository.loadFavorites()

    fun markFavorite() {
        if (isFavorite()) {
            favRepository.saveFavorites(favRepository.loadFavorites().filter { it != talkId })
        } else {
            favRepository.saveFavorites(favRepository.loadFavorites() + talkId)
        }
        loadDetails(talkId)
    }


}