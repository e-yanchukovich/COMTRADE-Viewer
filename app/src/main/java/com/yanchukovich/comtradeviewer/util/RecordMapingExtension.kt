package com.yanchukovich.comtradeviewer.util

import com.yanchukovich.comtradeviewer.model.Record
import com.yanchukovich.comtradeviewer.model.entity.RecordEntity

fun Record.toRecordEntity(): RecordEntity =
    RecordEntity(
        id,
        iedName,
        date,
        filePath
    )

fun RecordEntity.toRecord(): Record =
    Record(
        id,
        iedName,
        date,
        filePath
    )

fun List<RecordEntity>.toListRecords(): ArrayList<Record> =
    (map {
        it.toRecord()
    } as? ArrayList<Record>) ?: arrayListOf()