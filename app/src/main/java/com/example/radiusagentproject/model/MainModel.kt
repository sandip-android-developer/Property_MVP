package com.example.radiusagentproject.model

import android.annotation.SuppressLint
import android.widget.Toast
import com.example.radiusagentproject.R
import com.example.radiusagentproject.core.MainActvityContract
import com.example.radiusagentproject.repository.local.AppDatabase
import com.example.radiusagentproject.repository.local.Property
import com.example.radiusagentproject.repository.remote.api.ApiService
import com.example.radiusagentproject.repository.remote.model.Facilities
import com.example.radiusagentproject.utils.Common
import com.example.radiusagentproject.view.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainModel(
    private val apiService: ApiService,
    private val database: AppDatabase,
    private val activity: MainActivity
) :
    MainActvityContract.Model {

    @SuppressLint("CheckResult")
    override fun fetchProperty(onFinishListener: MainActvityContract.Model.OnFinishListener) {
        onFinishListener.onLoading()
        try {
            database.appDao().getProperty().observe(activity, androidx.lifecycle.Observer {
                if (it != null) {
                    val facilities = Facilities()
                    facilities.facilities = it.facilities
                    facilities.exclusions = it.exclusions
                    onFinishListener.onSuccess(facilities)
                }
            })

            if (!Common.isConnected(activity)) {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.please_check_your_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            Observable.interval(
                1, 86400,
                TimeUnit.SECONDS
            ).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Long> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onFinishListener.onError(message = e.message.toString())
                    }

                    override fun onComplete() {

                    }

                    override fun onNext(t: Long) {
                        apiService.getFacilities()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(object : io.reactivex.Observer<Facilities> {
                                override fun onSubscribe(d: Disposable) {
                                }

                                override fun onError(e: Throwable) {
                                    onFinishListener.onError(message = e.message.toString())
                                }

                                override fun onComplete() {
                                }

                                override fun onNext(facilities: Facilities) {
                                    if (facilities != null) {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            launch {
                                                val checkAlreadyDataExist: Boolean = try {
                                                    database.appDao().checkIfDataExist()
                                                } catch (e: Exception) {
                                                    false
                                                }
                                                if (checkAlreadyDataExist) {
                                                    database.appDao().updateProperty(
                                                        Property(
                                                            facilities = facilities.facilities,
                                                            exclusions = facilities.exclusions,
                                                            propertyId = 0
                                                        )
                                                    )
                                                } else {
                                                    database.appDao().insertProperty(
                                                        Property(
                                                            facilities = facilities.facilities,
                                                            exclusions = facilities.exclusions,
                                                            propertyId = 0
                                                        )
                                                    )
                                                }
                                            }

                                        }
                                        onFinishListener.onSuccess(facilities)

                                    } else {
                                        onFinishListener.onError(message = "Something went wrong")
                                    }
                                }
                            })
                    }

                })

        } catch (e: Exception) {
            onFinishListener.onError(message = e.message.toString())
        }
    }
}