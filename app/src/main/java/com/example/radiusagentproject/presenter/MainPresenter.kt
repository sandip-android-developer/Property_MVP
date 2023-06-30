package com.example.radiusagentproject.presenter

import com.example.radiusagentproject.core.MainActvityContract
import com.example.radiusagentproject.repository.remote.model.Facilities
import com.example.radiusagentproject.utils.launchOnMain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class MainPresenter(
    private val view: MainActvityContract.View,
    private val model: MainActvityContract.Model
) : MainActvityContract.Presenter, MainActvityContract.Model.OnFinishListener {
    private val scope = CoroutineScope(Dispatchers.IO)
    override fun getPropertyDetails() {
        model.fetchProperty(onFinishListener = this@MainPresenter)
    }

    override fun onDestroy() {
        scope.cancel()
    }

    override fun onLoading() {
        scope.launchOnMain { view.onLoading() }
    }

    override fun onError(message: String) {
        scope.launchOnMain { view.onError(message) }
    }

    override fun onSuccess(facilities: Facilities) {
        scope.launchOnMain {
            view.onSuccess(facilities)
        }
    }
}