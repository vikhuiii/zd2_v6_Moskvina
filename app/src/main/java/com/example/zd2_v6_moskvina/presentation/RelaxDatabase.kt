package com.example.zd2_v6_moskvina.presentation

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RelaxEntry::class], version = 1)
abstract class RelaxDatabase : RoomDatabase() {
    abstract fun relaxDao(): RelaxDao

    companion object {
        @Volatile private var INSTANCE: RelaxDatabase? = null
        fun getDatabase(ctx: Context): RelaxDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(ctx, RelaxDatabase::class.java, "relax_db").build()
                    .also { INSTANCE = it }
            }
    }
}