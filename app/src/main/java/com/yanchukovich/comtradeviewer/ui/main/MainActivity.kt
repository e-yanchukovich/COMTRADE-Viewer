package com.yanchukovich.comtradeviewer.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yanchukovich.comtradeviewer.R
import com.yanchukovich.comtradeviewer.databinding.ActivityMainBinding
import com.yanchukovich.comtradeviewer.ui.navigation.NavigationFragment
import com.yanchukovich.comtradeviewer.ui.onboarding.OnboardingFragment
import com.yanchukovich.comtradeviewer.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.getIsFirstOpen()) {
            viewModel.setIsFirstOpen(false)
            supportFragmentManager.replaceFragment(R.id.containerMain, OnboardingFragment())
        } else {
            supportFragmentManager.replaceFragment(R.id.containerMain, NavigationFragment())
        }
    }
}