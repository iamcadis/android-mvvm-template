package com.apps.utils.extensions

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun View.visibleOrGone(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.visibleOrInvisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

fun RecyclerView.toLinear(
        viewAdapter: RecyclerView.Adapter<*>,
        orientation: Int = RecyclerView.VERTICAL,
        isHasFixedSize: Boolean = true
) {
    this.apply {
        setHasFixedSize(isHasFixedSize)
        layoutManager = LinearLayoutManager(context, orientation, false)
        itemAnimator = DefaultItemAnimator()
        adapter = viewAdapter
    }
}

fun RecyclerView.toGrid(
        viewAdapter: RecyclerView.Adapter<*>,
        column: Int,
        isHasFixedSize: Boolean = true
) {
    this.apply {
        setHasFixedSize(isHasFixedSize)
        layoutManager = GridLayoutManager(context, column)
        itemAnimator = DefaultItemAnimator()
        adapter = viewAdapter
    }
}