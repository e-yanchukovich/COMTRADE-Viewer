package com.yanchukovich.comtradeviewer.model

data class IEDConfig(
    var stationName: String = "",
    var id: String = "",
    var revVersion: String = "",
    var channelNumber: Int = 0,
    var analogChannelNumber: Int = 0,
    var discreteChannelNumber: Int = 0,
    var analogChannelsList: List<AnalogChannel> = listOf(),
    var discreteChannelList: List<DiscreteChannel> = listOf(),
    var gridFrequency: Float = 0.0f,
    var nRates: Int = 0,
    var sampleOptionList: List<SampleOption> = listOf(),
    var startTime: String = "",
    var triggerTime: String = "",
    var fileType: String = "",
)
