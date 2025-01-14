package com.weihua.tintinttest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PhotoDao {

    companion object {
        const val INFO_TABLE_NAME = "photos"
    }

    @Query(
        """
        SELECT * FROM $INFO_TABLE_NAME 
        WHERE id >= :startId 
        ORDER BY id ASC 
        LIMIT :pageSize
    """
    )
    suspend fun getPhotosByPage(startId: Int, pageSize: Int): List<PhotoEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<PhotoEntity>)
}