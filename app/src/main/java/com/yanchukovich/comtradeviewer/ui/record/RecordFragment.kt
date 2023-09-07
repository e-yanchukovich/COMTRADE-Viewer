package com.yanchukovich.comtradeviewer.ui.record

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yanchukovich.comtradeviewer.R
import com.yanchukovich.comtradeviewer.databinding.FragmentRecordBinding
import dagger.hilt.android.AndroidEntryPoint

private const val RECORD_EXTRA = "recordExtra"

@AndroidEntryPoint
class RecordFragment : Fragment(R.layout.fragment_record) {

    private val binding by viewBinding(FragmentRecordBinding::bind)

    private val viewModel: RecordFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filePath = arguments?.getString(RECORD_EXTRA)
        filePath?.let {
            viewModel.openRecordConfig(it)
        }

        viewModel.apply {
            config.observe(viewLifecycleOwner) { config ->
                binding.recordConfig.stationNameTextView.text = config.stationName
                binding.recordConfig.idTextView.text = config.id
                binding.recordConfig.fileTypeTextView.text = config.fileType
                binding.recordConfig.analogChannelNumberTextView.text =
                    config.analogChannelNumber.toString()
                binding.recordConfig.discreteChannelNumberTextView.text =
                    config.discreteChannelNumber.toString()
                binding.recordConfig.gridFrequencyTextView.text = config.gridFrequency.toString()
                binding.recordConfig.startTimeTextView.text = config.startTime
                binding.recordConfig.triggerTimeTextView.text = config.triggerTime
            }

            lineData.observe(viewLifecycleOwner) { lineData ->
                binding.recordChart.lineChart.data = lineData
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.config -> {
                    binding.recordConfig.root.visibility = View.VISIBLE
                    binding.recordChart.root.visibility = View.GONE
                    return@setOnItemSelectedListener true
                }

                R.id.chart -> {
                    binding.recordConfig.root.visibility = View.GONE
                    binding.recordChart.root.visibility = View.VISIBLE
                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener true
            }
        }

        binding.bottomNavigationView.selectedItemId = R.id.config
    }

    companion object {
        const val TAG = "RecordFragment"

        fun getFragment(recordPath: String) = RecordFragment().apply {
            arguments = Bundle().apply {
                putString(RECORD_EXTRA, recordPath)
            }
        }
    }
}