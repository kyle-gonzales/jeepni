package com.example.jeepni.core.data

import androidx.room.*
import com.example.jeepni.core.data.model.AlarmContent
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmContentDao { //Data Access Object (methods that can be used to access the data from the db)

    @Query("SELECT * FROM alarm")
    fun getAllAlarms() : Flow<List<AlarmContent>>

    @Query("SELECT * FROM alarm WHERE id = :id")
    suspend fun getAlarmById(id: Int) : AlarmContent?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: AlarmContent)

    @Delete
    suspend fun deleteAlarm(alarm: AlarmContent)

}