package com.imran.hov.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imran.hov.data.model.UsersEntity
import com.imran.hov.utils.DATABASE_NAME

@Database(entities = [ UsersEntity::class], version = 1, exportSchema = false)
//@TypeConverters(UrlTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): UsersDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
