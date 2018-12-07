package io.monkeypatch.monkeyconf.app

import kotlinx.coroutines.launch

class TalkListPresenter(localView: TalkListView) : BasePresenter<TalkListView>(localView) {

    val repository = Container.conferenceRepository

    fun loadTalks() {
        launch {
            try {
                view.showLoading(true)
                val talks = repository.getConference()
                view.showTalks(talks)
                view.showLoading(false)
            } catch (e: Exception) {
                view.showLoading(false)
                view.displayError(e)
            }
        }
    }

    fun filter(text: String) {
        launch {
            try {
                val talks = repository.getConference()
                view.showTalks(talks.filter { it.title.contains(text, true) || it.description.contains(text, true) })
            } catch (e: Exception) {
                view.displayError(e)
            }
        }
    }

}