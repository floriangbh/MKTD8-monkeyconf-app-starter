package io.monkeypatch.monkeyconf.app

interface TalkListView : BaseView {

    fun showTalks(talks: List<Talk>)
    fun showLoading(loading: Boolean)

}