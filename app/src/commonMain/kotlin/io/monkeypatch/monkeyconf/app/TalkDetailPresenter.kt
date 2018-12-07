package io.monkeypatch.monkeyconf.app

import kotlinx.coroutines.launch

class TalkDetailPresenter(view: TalkDetailView) : BasePresenter<TalkDetailView>(view) {

    val repository = Container.conferenceRepository

    fun loadDetails(id: String) {
        launch {
            try {
                repository.getTalk(id)?.let {
                    view.displayTalk(it)
                }
            } catch (e: Exception) {
                view.displayError(e)
            }
        }
    }


}