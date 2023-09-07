package com.yanchukovich.comtradeviewer.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class RecordEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo("iedName")
    val iedName : String,
    @ColumnInfo("date")
    val date: String,
    @ColumnInfo("filePath")
    val filePath : String
)