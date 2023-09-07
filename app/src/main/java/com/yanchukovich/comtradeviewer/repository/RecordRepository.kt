package com.yanchukovich.comtradeviewer.repository

import com.yanchukovich.comtradeviewer.db.RecordDao
import com.yanchukovich.comtradeviewer.model.Record
import com.yanchukovich.comtradeviewer.model.entity.RecordEntity
import com.yanchukovich.comtradeviewer.util.toListRecords
import com.yanchukovich.comtradeviewer.util.toRecordEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordDao: RecordDao
) {

    suspend fun addRecord(record: Record) {
        recordDao.insertRecord(record.toRecordEntity())
    }

    suspend fun deleteRecord(record: Record) {
        recordDao.deleteRecord(record.toRecordEntity())
    }

    fun getAllRecords() = recordDao.getAllRecords()

    suspend fun isRecordExists(record: Record) = recordDao.isRecordExists(record.filePath)
}