package com.yanchukovich.comtradeviewer.ui.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yanchukovich.comtradeviewer.ui.onboarding.OnboardingStepFragment.Companion.getOnbordingStepFragment
import com.yanchukovich.comtradeviewer.ui.onboarding.STEP_1
import com.yanchukovich.comtradeviewer.ui.onboarding.STEP_2
import com.yanchukovich.comtradeviewer.ui.onboarding.STEP_3

@Suppress("DEPRECATION")
class OnbordingAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val listFragment =
        arrayListOf(
            getOnbordingStepFragment(STEP_1),
            getOnbordingStepFragment(STEP_2),
            getOnbordingStepFragment(STEP_3)
        )

    override fun getCount() = listFragment.size

    override fun getItem(position: Int): Fragment = listFragment[position]
}