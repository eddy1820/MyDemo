package com.example.mydemo.main.core.api.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydemo.R
import com.example.mydemo.databinding.ItemDividerBinding
import com.example.mydemo.databinding.ItemNewsBinding
import com.example.mydemo.model.VectorResponse.Item
import java.text.SimpleDateFormat

class VectorAdapter(private val onItemClick: (String) -> Unit) :
  ListAdapter<Item, RecyclerView.ViewHolder>(diffUtil) {

  companion object {
    private val diffUtil = object : DiffUtil.ItemCallback<Item>() {
      override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return true
      }

      override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
      }
    }
  }

  enum class ItemType(val key: String) {
    DIVIDER("divider"), NEWS("news")
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      ItemType.DIVIDER.ordinal -> {
        DividerItemViewHolder(ItemDividerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
      }
      ItemType.NEWS.ordinal -> {
        NewsItemViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
      }
      else -> {
        DividerItemViewHolder(ItemDividerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
      }
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is DividerItemViewHolder -> holder.bind(currentList[position])
      is NewsItemViewHolder -> holder.bind(currentList[position])
    }
  }

  override fun getItemViewType(position: Int): Int {
    return when (currentList[position].type) {
      ItemType.DIVIDER.key -> ItemType.DIVIDER.ordinal
      ItemType.NEWS.key -> ItemType.NEWS.ordinal
      else -> R.layout.item_divider
    }
  }

  inner class DividerItemViewHolder(private val binding: ItemDividerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Item) {
      binding.titleLabel.text = data.title
    }
  }

  inner class NewsItemViewHolder(private val binding: ItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Item) {
      binding.mainTitleLabel.text = data.appearance.mainTitle
      binding.subTitleLabel.text = data.appearance.subTitle
      data.extra?.created?.let {
        binding.createdLabel.text = SimpleDateFormat().format(it)
      }
      binding.subscriptLabel.text = data.appearance.subscript
      Glide.with(binding.root)
        .load(data.appearance.thumbnail)
        .into(binding.img)
      binding.root.setOnClickListener { onItemClick.invoke(data.ref) }
    }
  }
}