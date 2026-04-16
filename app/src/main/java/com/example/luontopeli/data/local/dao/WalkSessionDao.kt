package com.example.luontopeli.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.luontopeli.data.local.entity.WalkSession
import kotlinx.coroutines.flow.Flow

@Dao
interface WalkSessionDao {

    @Query("SELECT * FROM walk_sessions ORDER BY startTime DESC")
    fun getAll(): Flow<List<WalkSession>>

    @Query("SELECT * FROM walk_sessions ORDER BY startTime DESC")
    fun getAllSessions(): Flow<List<WalkSession>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: WalkSession)

    @Update
    suspend fun update(session: WalkSession)

    @Query("SELECT * FROM walk_sessions WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveSession(): WalkSession?

    @Query("SELECT COUNT(*) FROM walk_sessions")
    fun getCount(): Flow<Int>
}