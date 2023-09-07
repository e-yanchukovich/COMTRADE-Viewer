package com.yanchukovich.comtradeviewer.model

data class AnalogChannel(
    val number: Int,
    val id: String,
    val phaseId: String,
    val componentName: String,
    val unit: String,
    val calibration: Float,
    val offset: Float,
    val timeShift: Float,
    val min: Int,
    val max: Int,
    val primary: Int,
    val secondary: Int,
    val selector: String
)
