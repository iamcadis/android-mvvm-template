package com.apps.utils.extensions

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import com.apps.R

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.visibleOrInvisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

fun ImageView.show(url: String?) {
    val imageLoader = ImageLoader(this.context)
    val request = ImageRequest.Builder(this.context)
            .data(url)
            .crossfade(true)
            .error(R.drawable.svg_no_image)
            .placeholder(R.drawable.svg_placeholder)
            .target(
                    onStart = { placeholder ->
                        setBackgroundColor(ContextCompat.getColor(context, R.color.lighter_gray))
                        scaleType = ImageView.ScaleType.CENTER_INSIDE
                        setImageDrawable(placeholder)
                    },
                    onSuccess = { result ->
                        setBackgroundColor(ContextCompat.getColor(context, R.color.lighter_gray))
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageDrawable(result)
                    },
                    onError = { error ->
                        setBackgroundColor(ContextCompat.getColor(context, R.color.lighter_gray))
                        scaleType = ImageView.ScaleType.CENTER_INSIDE
                        setImageDrawable(error)
                    }
            )
            .build()
    imageLoader.enqueue(request)
}

fun RecyclerView.toLinear(
        viewAdapter: RecyclerView.Adapter<*>,
        orientation: Int = RecyclerView.VERTICAL,
        isHasFixedSize: Boolean = true
) {
    apply {
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
    apply {
        setHasFixedSize(isHasFixedSize)
        layoutManager = GridLayoutManager(context, column)
        itemAnimator = DefaultItemAnimator()
        adapter = viewAdapter
    }
}