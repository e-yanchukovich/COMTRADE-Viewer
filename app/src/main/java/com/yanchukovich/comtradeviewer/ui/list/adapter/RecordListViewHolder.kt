package com.yanchukovich.comtradeviewer.ui.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.yanchukovich.comtradeviewer.databinding.ItemRecordBinding
import com.yanchukovich.comtradeviewer.model.Record

class RecordListViewHolder(private val binding: ItemRecordBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(record: Record) {
        binding.run {
            itemName.text = record.iedName
            itemDate.text = record.date
            itemPath.text= record.filePath
        }
    }
}