package com.yanchukovich.comtradeviewer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yanchukovich.comtradeviewer.model.entity.RecordEntity

@Database(entities = [RecordEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getRecordDao(): RecordDao
}