package com.jshu.akarshit.models

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "apps")
data class AppEntity(
    @PrimaryKey
    val appId: Int,

    val appName: String,
    val appIcon: String,
    val packageName: String,
    val status: String,
    //Insert Column
    val isBlocked: Boolean = false
)

