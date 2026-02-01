package com.jshu.akarshit.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jshu.akarshit.di.AppDatabase
import com.jshu.akarshit.api.AppDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_db"
        )
            //.fallbackToDestructiveMigration()
            //“If the database schema changes and I didn’t provide a migration,
            //just delete the old database and create a new one.”

            .addMigrations(MIGRATION_2_3)

            .build()

    @Provides
    fun provideAppDao(db: AppDatabase): AppDao = db.appDao()
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            ALTER TABLE apps
            ADD COLUMN isBlocked INTEGER NOT NULL DEFAULT 0
            """.trimIndent()
        )
    }
}

