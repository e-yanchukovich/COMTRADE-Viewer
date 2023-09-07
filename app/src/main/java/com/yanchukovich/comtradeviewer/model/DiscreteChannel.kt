package com.yanchukovich.comtradeviewer.model

data class DiscreteChannel(
    val number: Int,
    val id: String,
    val phaseId: String,
    val componentName: String,
    val normalState: Int,
)
