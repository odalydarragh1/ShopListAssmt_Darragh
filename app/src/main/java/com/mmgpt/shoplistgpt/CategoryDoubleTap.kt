package com.mmgpt.shoplistgpt

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

/**
 * Double-tap a category row to open its page.
 *
 * The listener sits in front of the row so a clickable card cannot swallow
 * the taps. The page change is posted because ViewPager2 is still in this
 * gesture and will snap back to the page under the finger if currentItem
 * is set before the gesture ends.
 */
fun RecyclerView.bindCategoryDoubleTap(
    planMode: Boolean,
    categoryIdAt: (adapterPosition: Int) -> Long?,
    categoryIds: () -> List<Long>,
    openPage: (page: Int) -> Unit,
) {
    val detector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            val child = findChildViewUnder(e.x, e.y) ?: return false
            val row = getChildViewHolder(child).bindingAdapterPosition
            if (row == RecyclerView.NO_POSITION) return false
            val id = categoryIdAt(row) ?: return false
            val page = CategoryNavigation.pageFor(
                planMode,
                CategoryNavigation.indexOfId(categoryIds(), id),
            ) ?: return false
            post { openPage(page) }
            return true
        }
    })
    addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val handled = detector.onTouchEvent(e)
            if (handled) parent?.requestDisallowInterceptTouchEvent(true)
            return handled
        }
    })
}
