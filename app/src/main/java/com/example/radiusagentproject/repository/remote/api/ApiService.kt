package com.example.radiusagentproject.repository.remote.api

import com.example.radiusagentproject.repository.remote.model.Facilities
import com.example.radiusagentproject.utils.AppConstant
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {
    @GET(AppConstant.ASSIGNMENT_DB)
    fun getFacilities(): Observable<Facilities>
}