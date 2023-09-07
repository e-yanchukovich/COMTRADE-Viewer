package com.yanchukovich.comtradeviewer.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yanchukovich.comtradeviewer.model.Record
import com.yanchukovich.comtradeviewer.model.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: RecordEntity)

    @Delete
    suspend fun deleteRecord(record: RecordEntity)

    @Query("SELECT * FROM records")
    fun getAllRecords(): Flow<List<RecordEntity>>

    @Query("SELECT EXISTS (SELECT * FROM records WHERE filePath = :filePath)")
    suspend fun isRecordExists(filePath: String) : Boolean

}