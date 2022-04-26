package com.example.mydemo.main.core.room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mydemo.databinding.ItemCurrencyBinding
import com.example.mydemo.model.CurrencyInfo
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class CurrencyAdapter(private val onItemClick: (CurrencyInfo) -> Unit) :
  ListAdapter<CurrencyInfo, CurrencyAdapter.CurrencyItemViewHolder>(diffUtil) {
  private var isSortById = true
  private val reentrantLock = ReentrantLock()

  companion object {
    private val diffUtil = object : DiffUtil.ItemCallback<CurrencyInfo>() {
      override fun areItemsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return true
      }

      override fun areContentsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return oldItem == newItem
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {
    return CurrencyItemViewHolder(ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {
    (holder as? CurrencyItemViewHolder)?.bind(currentList[position])
  }

  fun sortList() {
    reentrantLock.withLock {
      if (currentList.isEmpty()) return
      if (isSortById) {
        isSortById = false
        submitList(currentList.sortedBy { it.name })
      } else {
        isSortById = true
        submitList(  currentList.sortedBy { it.id })
      }
    }
  }

  override fun submitList(list: List<CurrencyInfo>?) {
    reentrantLock.withLock {
      super.submitList(list)
    }
  }

  inner class CurrencyItemViewHolder(private val binding: ItemCurrencyBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: CurrencyInfo) {
      binding.symbolLabel.text = data.getFirstLetterOfSymbol()
      binding.nameLabel.text = data.name
      binding.idLabel.text = data.id
      binding.root.setOnClickListener { onItemClick.invoke(data) }
    }
  }
}