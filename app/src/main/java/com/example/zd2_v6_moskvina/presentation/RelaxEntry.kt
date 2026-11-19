package com.example.zd2_v6_moskvina.presentation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "relax_entries")
data class RelaxEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mood: String,
    val stressLevel: Int,
    val date: Long = System.currentTimeMillis()
)