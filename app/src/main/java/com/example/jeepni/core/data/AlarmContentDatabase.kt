package com.example.jeepni.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jeepni.core.data.model.AlarmContent

@Database(
    entities = [AlarmContent::class],
    version = 1
)
abstract class AlarmContentDatabase : RoomDatabase() {

    abstract val dao : AlarmContentDao

}