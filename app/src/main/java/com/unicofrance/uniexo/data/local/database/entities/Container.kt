package com.unicofrance.uniexo.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Container(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "producing_place_label") val producingPlaceLabel: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "stream_label") val streamLabel: String,
    @ColumnInfo(name = "stream_color") val streamColor: String,
    @ColumnInfo(name = "icon_id") val iconId: Int,
    @ColumnInfo(name = "creation_datetime") val creationDatetime: String
)