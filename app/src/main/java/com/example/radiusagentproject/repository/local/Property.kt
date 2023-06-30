package com.example.radiusagentproject.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.radiusagentproject.repository.remote.model.Exclusion
import com.example.radiusagentproject.repository.remote.model.Facility

@Entity(tableName = "property")
data class Property(
    @PrimaryKey(autoGenerate = true)
    val propertyId: Int,
    val exclusions: List<List<Exclusion>>,
    val facilities: List<Facility>
)
