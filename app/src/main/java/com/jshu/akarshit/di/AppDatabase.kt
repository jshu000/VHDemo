package com.jshu.akarshit.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jshu.akarshit.api.AppDao
import com.jshu.akarshit.models.AppEntity

@Database(
    entities = [AppEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}