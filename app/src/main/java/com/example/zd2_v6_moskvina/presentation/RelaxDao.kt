package com.example.zd2_v6_moskvina.presentation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RelaxDao {
    @Query("SELECT * FROM relax_entries ORDER BY date DESC")
    fun getAllEntries(): Flow<List<RelaxEntry>>

    @Insert
    suspend fun insertEntry(entry: RelaxEntry)

    @Delete
    suspend fun deleteEntry(entry: RelaxEntry)
}