package com.heshan.domee

import androidx.room.Entity
import androidx.room.PrimaryKey


// Entity class for Room database

@Entity(tableName = "DoMee")
data class Entity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title:String,
    var priority:String
)