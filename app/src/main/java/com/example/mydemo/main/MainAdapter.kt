package com.example.mydemo.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mydemo.databinding.ItemMainBinding
import com.example.mydemo.view.mian.PageItem

class MainAdapter(private val onItemClick: (PageItem) -> Unit) :
    ListAdapter<PageItem, MainAdapter.CurrencyItemViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PageItem>() {
            override fun areItemsTheSame(oldItem: PageItem, newItem: PageItem): Boolean {
                return true
            }

            override fun areContentsTheSame(oldItem: PageItem, newItem: PageItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {
        return CurrencyItemViewHolder(
            ItemMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {
        (holder as? CurrencyItemViewHolder)?.bind(currentList[position])
    }


    override fun submitList(list: List<PageItem>?) {
        super.submitList(list)
    }

    inner class CurrencyItemViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PageItem) {
            binding.titleLabel.text = data.title
            binding.root.setOnClickListener {
                onItemClick.invoke(data)
            }
        }
    }
}