package com.example.radiusagentproject.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.radiusagentproject.R
import com.example.radiusagentproject.core.MainActvityContract
import com.example.radiusagentproject.core.BaseActivity
import com.example.radiusagentproject.databinding.ActivityMainBinding
import com.example.radiusagentproject.model.MainModel
import com.example.radiusagentproject.presenter.MainPresenter
import com.example.radiusagentproject.repository.local.AppDatabase
import com.example.radiusagentproject.repository.remote.api.ApiService
import com.example.radiusagentproject.repository.remote.model.Exclusion
import com.example.radiusagentproject.repository.remote.model.Facilities
import com.example.radiusagentproject.repository.remote.model.Facility
import com.example.radiusagentproject.view.adapter.FacilitiesAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(), MainActvityContract.View {

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var database: AppDatabase

    var facilitiesList: MutableList<Facility> = mutableListOf()
    var exclusionList: MutableList<List<Exclusion>> = mutableListOf()
    var disposable: Disposable? = null

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = MainPresenter(this, MainModel(apiService,database,this))
        initView()
    }
    private val bindingMainActivity by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    @SuppressLint("CheckResult")
    private fun initView() {
        initRecyclerView()
        presenter.getPropertyDetails()

    }

    private fun initRecyclerView() {
        bindingMainActivity.recyclerView.setHasFixedSize(true)
        bindingMainActivity.recyclerView.adapter = FacilitiesAdapter(
            this,
            facilitiesList,
            exclusionList
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        disposable?.dispose()
    }

    override fun onLoading() {
        showProgress(this)
    }

    override fun onSuccess(facilities: Facilities) {
        hideProgress()
        if (facilities.facilities != null && facilities.facilities.isNotEmpty()) {
            facilitiesList = facilities.facilities as MutableList<Facility>
            bindingMainActivity.recyclerView.adapter = FacilitiesAdapter(this, facilitiesList,facilities.exclusions)
        }
    }

    override fun onError(message: String) {
        hideProgress()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}