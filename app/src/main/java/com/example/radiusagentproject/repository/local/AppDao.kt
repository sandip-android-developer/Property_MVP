package com.example.radiusagentproject.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AppDao {
    @Insert
    suspend fun insertProperty(property: Property)

    @Update
    suspend fun updateProperty(contact: Property)

    @Query("SELECT * FROM PROPERTY where propertyId > 0")
    fun checkIfDataExist(): Boolean

    @Query("SELECT * FROM PROPERTY")
    fun getProperty(): LiveData<Property>

}