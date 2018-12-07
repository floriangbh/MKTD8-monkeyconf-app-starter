package io.monkeypatch.monkeyconf.app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

interface BaseView {
    fun displayError(e: Exception)
}

abstract class BasePresenter<T : BaseView>(
    val view: T
) : CoroutineScope {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    open fun onCreate() {
        job = Job()
    }

    open fun onDestroy() {
        job.cancel()
    }
}