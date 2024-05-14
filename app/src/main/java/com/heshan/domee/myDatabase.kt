package com.heshan.domee

import androidx.room.Database
import androidx.room.RoomDatabase

// Database class for Room database
@Database(entities = [Entity::class],version=1)
abstract class myDatabase : RoomDatabase() {
    abstract fun dao():DAO
}