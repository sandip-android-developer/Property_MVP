package com.example.radiusagentproject.repository.local

import androidx.room.TypeConverter
import com.example.radiusagentproject.repository.remote.model.Exclusion
import com.example.radiusagentproject.repository.remote.model.Facility
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromListFacilitiesToString(list: List<Facility>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToListFacilities(value: String): List<Facility> {
        val listType: Type = object : TypeToken<List<Facility>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListExclusionsToString(list: List<List<Exclusion>>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToListExclusion(value: String): List<List<Exclusion>> {
        val listType: Type = object : TypeToken<List<List<Exclusion>>>() {}.type
        return Gson().fromJson(value, listType)
    }
}