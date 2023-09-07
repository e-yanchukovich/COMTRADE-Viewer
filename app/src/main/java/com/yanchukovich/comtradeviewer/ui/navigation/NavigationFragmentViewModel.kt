package com.yanchukovich.comtradeviewer.ui.navigation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanchukovich.comtradeviewer.repository.RecordFileRepository
import com.yanchukovich.comtradeviewer.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationFragmentViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val recordFileRepository: RecordFileRepository
) : ViewModel() {

    var showError: (() -> Unit)? = null

    var showRecordNotExists: (() -> Unit)? = null

    fun addRecord(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val record = recordFileRepository.getRecord(uri)

            if (record == null) {
                showError?.invoke()
            } else if (
                recordRepository.isRecordExists(record)
            ) {
                showRecordNotExists?.invoke()
            } else {
                recordRepository.addRecord(record)
            }
        }
    }
}