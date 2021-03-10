package com.apps.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any, VB : ViewDataBinding> : RecyclerView.Adapter<BaseAdapter.Companion.BaseViewHolder<VB>>() {

    companion object {
        class BaseViewHolder<VB : ViewDataBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
    }

    var listener: ((view: View, position: Int, item: T) -> Unit)? = null
    val items = mutableListOf<T>()

    abstract fun getLayout(): Int
    override fun getItemCount() = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder<VB>(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayout(),
            parent,
            false
        )
    )

    fun set(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    fun append(element: T) {
        this.items.add(element)
        this.notifyItemInserted(itemCount)
    }

    fun append(contentsOf: List<T>) {
        this.items.addAll(contentsOf)
        this.notifyItemRangeInserted(this.items.size, contentsOf.size)
    }

    fun delete(index: Int) {
        this.items.removeAt(index)
        this.notifyItemRemoved(index)
        this.notifyItemRangeChanged(index, items.size)
    }
}