package com.example.radiusagentproject.utils

import android.content.Context

object PrefUtils {
    var OPTION_ID = "option_id"
    var FACILITY_ID = "facility_id"

    fun setOptionId(ctx: Context?, value: String?) {
        Prefs.with(ctx!!).save(OPTION_ID, value!!)
    }

    fun getOptionId(ctx: Context?): String? {
        return Prefs.with(ctx!!).getString(OPTION_ID, "")
    }

    fun setFacilityId(ctx: Context?, value: String?) {
        Prefs.with(ctx!!).save(FACILITY_ID, value!!)
    }

    fun getFacilityId(ctx: Context?): String? {
        return Prefs.with(ctx!!).getString(FACILITY_ID, "")
    }
}