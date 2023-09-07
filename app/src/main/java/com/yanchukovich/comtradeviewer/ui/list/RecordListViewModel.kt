package com.yanchukovich.comtradeviewer.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanchukovich.comtradeviewer.model.Record
import com.yanchukovich.comtradeviewer.model.entity.RecordEntity
import com.yanchukovich.comtradeviewer.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordListViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {

    fun delete(record: Record) {
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.deleteRecord(record)
            getAllRecords()
        }
    }

    fun getAllRecords(): Flow<List<RecordEntity>> = recordRepository.getAllRecords()

    fun deleteFile(record: Record) {
        //TODO
    }
}
