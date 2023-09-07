package com.yanchukovich.comtradeviewer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanchukovich.comtradeviewer.model.Record
import com.yanchukovich.comtradeviewer.repository.RecordRepository
import com.yanchukovich.comtradeviewer.repository.SharedPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferenceRepository,
) : ViewModel() {

    fun getIsFirstOpen(): Boolean {
        return sharedPreferencesRepository.getIsFirstOpen()
    }

    fun setIsFirstOpen(result: Boolean) {
        sharedPreferencesRepository.setIsFirstOpen(result)
    }
}