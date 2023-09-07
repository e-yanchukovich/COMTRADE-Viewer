package com.yanchukovich.comtradeviewer.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yanchukovich.comtradeviewer.R
import com.yanchukovich.comtradeviewer.databinding.FragmentOnbordingStepBinding

const val STEP_1 = 1
const val STEP_2 = 2
const val STEP_3 = 3

private const val STEP_EXTRA = "stepExtra"

class OnboardingStepFragment : Fragment(R.layout.fragment_onbording_step) {

    private val binding by viewBinding(FragmentOnbordingStepBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (arguments?.getInt(STEP_EXTRA) ?: STEP_1) {
            STEP_1 -> {
                binding.onboardingTitleTextView.setText(R.string.onbording_step_1_title)
                binding.imageView.setImageResource(R.drawable.ic_chart)
                binding.onboardingTextView.setText(R.string.onbording_step_1_text)
            }

            STEP_2 -> {
                binding.onboardingTitleTextView.setText(R.string.onbording_step_2_title)
                binding.imageView.setImageResource(R.drawable.ic_chart)
                binding.onboardingTextView.setText(R.string.onbording_step_2_text)
            }

            STEP_3 -> {
                binding.onboardingTitleTextView.setText(R.string.onbording_step_3_title)
                binding.imageView.setImageResource(R.drawable.ic_chart)
                binding.onboardingTextView.setText(R.string.onbording_step_3_text)
            }
        }
    }

    companion object {
        fun getOnbordingStepFragment(stepNumber: Int): OnboardingStepFragment {
            return OnboardingStepFragment().apply {
                arguments = bundleOf(STEP_EXTRA to stepNumber)
            }
        }
    }
}