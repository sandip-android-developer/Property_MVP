package com.example.radiusagentproject.repository.remote.model

import com.google.gson.annotations.SerializedName

open class Exclusion {
    @SerializedName("facility_id")
    val facilityId: String = ""

    @SerializedName("options_id")
    val optionsId: String = ""
}