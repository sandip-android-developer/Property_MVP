package com.example.radiusagentproject.repository.remote.model

import com.google.gson.annotations.SerializedName

open class Facility {
    @SerializedName("facility_id")
    val facilityId: String = ""

    @SerializedName("name")
    val name: String = ""

    @SerializedName("options")
    val options: List<Option> = listOf()
}