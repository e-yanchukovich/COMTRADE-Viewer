package com.yanchukovich.comtradeviewer.ui.record

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.LineData
import com.yanchukovich.comtradeviewer.model.IEDConfig
import com.yanchukovich.comtradeviewer.repository.RecordFileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordFragmentViewModel @Inject constructor(
    private val recordFileRepository: RecordFileRepository
) : ViewModel() {

    val config = MutableLiveData<IEDConfig>()
    val lineData = MutableLiveData<LineData>()

    fun openRecordConfig(filePath: String) {
        config.value = recordFileRepository.openRecordConfig(filePath)
        openChartData(filePath)
    }

    private fun openChartData(filePath: String) {
        lineData.postValue(config.value?.let { recordFileRepository.openRecordChart(filePath, it) })
    }
}