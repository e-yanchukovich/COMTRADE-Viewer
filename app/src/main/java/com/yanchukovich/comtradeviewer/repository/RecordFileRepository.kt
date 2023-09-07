package com.yanchukovich.comtradeviewer.repository

import android.net.Uri
import android.util.Log
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.yanchukovich.comtradeviewer.model.AnalogChannel
import com.yanchukovich.comtradeviewer.model.DiscreteChannel
import com.yanchukovich.comtradeviewer.model.IEDConfig
import com.yanchukovich.comtradeviewer.model.Record
import com.yanchukovich.comtradeviewer.model.SampleOption
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import javax.inject.Inject

class RecordFileRepository @Inject constructor() {

    fun openRecordConfig(file: File): IEDConfig {

        val config = IEDConfig()

        if (!file.exists()) {
            return IEDConfig()
        }

        try {
            val reader = BufferedReader(FileReader(file))

            //station_name, rec_dev_id, rev_year
            var line = reader.readLine()
            var splitString = line.split(',')
            if (splitString.size == 3) {
                config.stationName = splitString[0]
                config.id = splitString[1]
                config.revVersion = splitString[2]
            }

            //TT, #A, #D
            line = reader.readLine()
            splitString = line.split(',')
            if (splitString.size == 3) {
                config.channelNumber = splitString[0].toInt()
                if (splitString[1].last() == 'A') {
                    config.analogChannelNumber =
                        splitString[1].substring(0, splitString[1].length - 1).toInt()
                } else {
                    config.discreteChannelNumber =
                        splitString[1].substring(0, splitString[1].length - 1).toInt()
                }
                if (splitString[2].last() == 'A') {
                    config.analogChannelNumber =
                        splitString[1].substring(0, splitString[2].length - 1).toInt()
                } else {
                    config.discreteChannelNumber =
                        splitString[1].substring(0, splitString[2].length - 1).toInt()
                }
            }

            //An, ch_id, ph, ccbm, uu, a, b, skew, min, max, primary, secondary, P or S
            val analogList = mutableListOf<AnalogChannel>()

            for (i in 0 until config.analogChannelNumber) {
                line = reader.readLine()
                splitString = line.split(',')
                if (splitString.size == 13) {
                    analogList.add(
                        AnalogChannel(
                            splitString[0].toInt(),
                            splitString[1],
                            splitString[2],
                            splitString[3],
                            splitString[4],
                            splitString[5].toFloat(),
                            splitString[6].toFloat(),
                            splitString[7].toFloat(),
                            splitString[8].toInt(),
                            splitString[9].toInt(),
                            splitString[10].toInt(),
                            splitString[11].toInt(),
                            splitString[12],
                        )
                    )
                }
            }

            config.analogChannelsList = analogList

            //Dn, ch_id, ph, ccbm, y
            val discreteList = mutableListOf<DiscreteChannel>()

            for (i in 0 until config.discreteChannelNumber) {
                line = reader.readLine()
                splitString = line.split(',')
                if (splitString.size == 5) {
                    discreteList.add(
                        DiscreteChannel(
                            splitString[0].toInt(),
                            splitString[1],
                            splitString[2],
                            splitString[3],
                            splitString[4].toInt(),
                        )
                    )
                }
            }

            config.discreteChannelList = discreteList

            //line_freq
            line = reader.readLine()
            config.gridFrequency = line.toFloat()


            //nrates
            line = reader.readLine()
            config.nRates = line.toInt()

            //samp, endsamp
            val sampleOptionList: MutableList<SampleOption> = mutableListOf()

            for (i in 0 until config.nRates) {
                line = reader.readLine()
                splitString = line.split(',')

                if (splitString.size == 2) {
                    sampleOptionList.add(
                        SampleOption(
                            splitString[0].toFloat(), splitString[0].toInt()
                        )
                    )
                }
            }

            config.sampleOptionList = sampleOptionList

            //start_date, start_time
            line = reader.readLine()
            config.startTime = line

            //trigger_date, trigger_time
            line = reader.readLine()
            config.triggerTime = line

            //file_type
            line = reader.readLine()
            config.fileType = line

            //timemult
            //Todo

            //time_code, local_code
            //Todo

            //tmq_code, leapsec
            //Todo

            reader.close()

        } catch (e: NumberFormatException) {
            Log.e("NumberFormatException", e.stackTraceToString())
        } catch (ex: FileNotFoundException) {
            Log.e("FileNotFoundException", ex.stackTraceToString())
        } catch (ex: IOException) {
            Log.e("IOException", ex.stackTraceToString())
        }

        return config
    }

    fun openRecordConfig(filePath: String) = openRecordConfig(File(filePath))

    fun getRecord(uri: Uri): Record? {

        Log.e("my", uri.path.toString())
        val filePath = uri.path.toString().split(":")[1]
        val file = File(filePath)

        if (!file.exists()) {
            return null
        }

        try {
            val reader = BufferedReader(FileReader(file))

            val stationName: String
            val date: String

            //station_name
            var splitString = reader.readLine().split(',')
            if (splitString.size == 3) {
                stationName = splitString[0]
            } else {
                return null
            }

            splitString = reader.readLine().split(',')
            if (splitString.size == 3) {
                val rowNumber = splitString[1].substring(0, splitString[1].length - 1)
                    .toInt() + splitString[1].substring(0, splitString[2].length - 1).toInt()

                for (i in 0 until rowNumber + 3) {
                    reader.readLine()
                }
            }

            //date
            splitString = reader.readLine().split(',')
            if (splitString.size == 2) {
                date = splitString[0]
            } else {
                return null
            }

            reader.close()
            return Record(0, stationName, date, filePath)

        } catch (e: NumberFormatException) {
            Log.e("NumberFormatException", e.stackTraceToString())
        } catch (ex: FileNotFoundException) {
            Log.e("FileNotFoundException", ex.stackTraceToString())
        } catch (ex: IOException) {
            Log.e("IOException", ex.stackTraceToString())
        }

        return null
    }

    fun openRecordChart(filePath: String, config: IEDConfig): LineData {

        val dataFilePath = filePath.split(".")[0] + ".dat"
        val file = File(dataFilePath)

        if (!file.exists()) {
            return LineData()
        }

        val chartNumber = config.analogChannelNumber + config.discreteChannelNumber

        val linesDataList: ArrayList<ArrayList<Entry>> = arrayListOf()

        for (i in 0 until chartNumber) {
            linesDataList.add(arrayListOf())
        }

        try {
            val reader = BufferedReader(FileReader(file))

            var line = reader.readLine()
            var splitString = line.split(',')

            while (line != null) {
                for (i in 0 until chartNumber) {
                    linesDataList[i].add(Entry(splitString[0].toFloat(), splitString[i + 2].toFloat()))
                }

                splitString = line.split(',')
                line = reader.readLine()
            }

            val dataSets: ArrayList<ILineDataSet> = arrayListOf<ILineDataSet>()

            for (i in 0 until chartNumber) {
                dataSets.add(i, LineDataSet(linesDataList[i], i.toString()))
            }

            reader.close()

            return LineData(dataSets)

        } catch (e: NumberFormatException) {
            Log.e("NumberFormatException", e.stackTraceToString())
        } catch (ex: FileNotFoundException) {
            Log.e("FileNotFoundException", ex.stackTraceToString())
        } catch (ex: IOException) {
            Log.e("IOException", ex.stackTraceToString())
        }

        return LineData()
    }
}