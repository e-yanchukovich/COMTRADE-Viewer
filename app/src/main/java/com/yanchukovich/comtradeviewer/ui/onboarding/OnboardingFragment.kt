package com.yanchukovich.comtradeviewer.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yanchukovich.comtradeviewer.R
import com.yanchukovich.comtradeviewer.databinding.FragmentOnbordingBinding
import com.yanchukovich.comtradeviewer.ui.navigation.NavigationFragment
import com.yanchukovich.comtradeviewer.ui.onboarding.adapter.OnbordingAdapter
import com.yanchukovich.comtradeviewer.util.replaceFragment

class OnboardingFragment : Fragment(R.layout.fragment_onbording) {

    private val binding by viewBinding(FragmentOnbordingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.openListFragment.setOnClickListener {
            parentFragmentManager.replaceFragment(R.id.containerMain, NavigationFragment())
        }
        binding.viewPager.adapter = OnbordingAdapter(parentFragmentManager)
        binding.circleIndicator.setViewPager(binding.viewPager)
    }
}