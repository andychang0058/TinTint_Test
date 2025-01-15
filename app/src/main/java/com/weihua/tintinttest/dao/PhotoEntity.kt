package com.weihua.tintinttest.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weihua.tintinttest.api.jsonplaceholder.model.Photo

@Entity(tableName = PhotoDao.INFO_TABLE_NAME)
data class PhotoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "album_id") val albumId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "hex") val hex: String
)

fun PhotoEntity.toPhoto(): Photo {
    return Photo(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        hex = hex,
    )
}

fun Photo.toPhotoEntity(): PhotoEntity {
    return PhotoEntity(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        hex = hex
    )
}
