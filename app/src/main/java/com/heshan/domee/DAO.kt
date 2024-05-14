package com.heshan.domee

import androidx.room.*

@Dao
interface DAO {
    @Insert
    suspend fun insertTask(entity: Entity)

    @Update
    suspend fun updateTask(entity: Entity)

    @Delete
    suspend fun deleteTask(entity: Entity)

    @Query("DELETE FROM DoMee")
    suspend fun deleteAll()

//    @Query("SELECT * FROM DoMee")
//    suspend fun getTasks(): List<CardInfo>

    @Query("SELECT * FROM DoMee")
    suspend fun getTasks(): List<Entity>


    @Query("DELETE FROM DoMee WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)
}
