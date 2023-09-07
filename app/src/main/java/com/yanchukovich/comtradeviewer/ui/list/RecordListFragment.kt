package com.yanchukovich.comtradeviewer.ui.list

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yanchukovich.comtradeviewer.R
import com.yanchukovich.comtradeviewer.databinding.FragmentRecordsListBinding
import com.yanchukovich.comtradeviewer.model.Record
import com.yanchukovich.comtradeviewer.ui.list.adapter.RecordListAdapter
import com.yanchukovich.comtradeviewer.ui.record.RecordFragment
import com.yanchukovich.comtradeviewer.util.replaceFragment
import com.yanchukovich.comtradeviewer.util.toListRecords
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecordListFragment : Fragment(R.layout.fragment_records_list) {

    private val binding by viewBinding(FragmentRecordsListBinding::bind)

    private val viewModel: RecordListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getAllRecords().collect {
                setList(it.toListRecords())
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setList(list: ArrayList<Record>) {
        binding.recyclerView.run {
            if (adapter == null) {
                adapter = RecordListAdapter(
                    { record ->
                        parentFragmentManager.replaceFragment(
                            R.id.container,
                            RecordFragment.getFragment(record.filePath),
                            true
                        )
                    },
                    { record, view ->
                        showPopup(view, record)
                    }
                )
                layoutManager = LinearLayoutManager(requireContext())
            }
            (adapter as? RecordListAdapter)?.submitList(list)
            adapter?.notifyDataSetChanged()
        }
    }

    private fun showPopup(v: View, record: Record) {
        val popup = PopupMenu(requireContext(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_record, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    viewModel.delete(record)
                }

                R.id.deleteFile -> {
                    Toast.makeText(requireContext(), "Coming soon", Toast.LENGTH_LONG).show()
//                    showDeleteDialog(record)
                }

                R.id.share -> {
                    Toast.makeText(requireContext(), "Coming soon", Toast.LENGTH_LONG).show()
                }
            }
            return@setOnMenuItemClickListener true
        }
        popup.show()
    }

    private fun showDeleteDialog(record: Record) {
        AlertDialog.Builder(requireContext()).setTitle("Delete file?")
            .setMessage("Cannot be undone!")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteFile(record)
            }.setNegativeButton("No") { _, _ ->

            }.setNeutralButton("Cancel") { _, _ ->

            }.show()
    }
}