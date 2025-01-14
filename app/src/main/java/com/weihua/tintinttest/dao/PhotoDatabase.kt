package com.weihua.tintinttest.dao

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        PhotoEntity::class
    ],
    version = 1
)
abstract class PhotoDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "photo_db"

        fun createInstance(application: Application): PhotoDatabase {
            return Room.databaseBuilder(
                application,
                PhotoDatabase::class.java,
                DATABASE_NAME
            )
                .build()
        }
    }

    abstract fun photoDao(): PhotoDao
}