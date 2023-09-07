package com.yanchukovich.comtradeviewer.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yanchukovich.comtradeviewer.databinding.ItemRecordBinding
import com.yanchukovich.comtradeviewer.model.Record

class RecordListAdapter(
    private val onClick: (car: Record) -> Unit,
    private val onLongClick: (car: Record, view: View) -> Unit
) : ListAdapter<Record, RecordListViewHolder>(
    object : DiffUtil.ItemCallback<Record>() {
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return false
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordListViewHolder {
        return RecordListViewHolder(
            ItemRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecordListViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClick(getItem(position))
        }
        holder.itemView.setOnLongClickListener {
            onLongClick(getItem(position), it)
            return@setOnLongClickListener true
        }
    }
}