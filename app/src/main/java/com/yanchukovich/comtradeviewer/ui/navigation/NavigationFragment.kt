package com.yanchukovich.comtradeviewer.ui.navigation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yanchukovich.comtradeviewer.R
import com.yanchukovich.comtradeviewer.databinding.FragmentNavigationBinding
import com.yanchukovich.comtradeviewer.ui.list.RecordListFragment
import com.yanchukovich.comtradeviewer.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

private const val SUPPORT_EMAIL_ADDRESS = "mailto:yanchukovich@gmail.com"
private const val ABOUT_COMTRADE_ADDRESS = "https://sank6.ru/docs_comtrade_viewer/comtrade.html"

@AndroidEntryPoint
class NavigationFragment : Fragment(
    R.layout.fragment_navigation
) {

    private val binding by viewBinding(FragmentNavigationBinding::bind)

    private val viewModel: NavigationFragmentViewModel by viewModels()

    private val startOpenFileScreen = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { result ->
        result?.let {
            addRecord(it)
            parentFragmentManager.replaceFragment(R.id.container, RecordListFragment())
        }
    }

    private val requestPermissionScreen = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (!it) {
            Toast.makeText(
                requireContext(), getString(R.string.require_permission), Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            showError = {
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireContext(), getString(R.string.file_format_error), Toast.LENGTH_LONG
                    ).show()
                }
            }

            showRecordNotExists = {
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.file_already_in_list),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            binding.topAppBar.setNavigationOnClickListener {
                binding.drawerLayout.open()
            }

            binding.topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.addItem -> {
                        startOpenFileScreen.launch(arrayOf("*/*"))
                        true
                    }

                    R.id.itemList -> {
                        parentFragmentManager.replaceFragment(R.id.container, RecordListFragment())
                        true
                    }

                    else -> false
                }
            }

            binding.navigationView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.settings -> {
                        Toast.makeText(
                            requireContext(), getString(R.string.coming_soon), Toast.LENGTH_LONG
                        ).show()
                    }

                    R.id.about_comtrade -> {
                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(ABOUT_COMTRADE_ADDRESS)
                        })
                    }

                    R.id.send_email -> {
                        startActivity(Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse(SUPPORT_EMAIL_ADDRESS)
                        })
                    }
                }

                binding.drawerLayout.close()
                true
            }
        }

        parentFragmentManager.replaceFragment(R.id.container, RecordListFragment())

    }

    private fun addRecord(documentUri: Uri) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.addRecord(documentUri)
        } else {
            requestPermissionScreen.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }
}