package com.jshu.akarshit.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.jshu.akarshit.models.AppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("SELECT * FROM apps")
    fun getApps(): Flow<List<AppEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertApps(apps: List<AppEntity>)

    @Query("DELETE FROM apps")
    suspend fun clearApps()
}