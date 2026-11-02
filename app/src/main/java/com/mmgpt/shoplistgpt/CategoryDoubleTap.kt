package com.mmgpt.shoplistgpt

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.bindCategoryDoubleTap(
    planMode: Boolean,
    categoryIdAt: (adapterPosition: Int) -> Long?,
    categoryIds: () -> List<Long>,
    openPage: (page: Int) -> Unit,
) {
    var pendingPage: Int? = null
    val detector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            val child = findChildViewUnder(e.x, e.y) ?: return false
            val row = findContainingViewHolder(child)?.bindingAdapterPosition
                ?: RecyclerView.NO_POSITION
            if (row == RecyclerView.NO_POSITION) return false
            val id = categoryIdAt(row) ?: return false
            pendingPage = CategoryNavigation.pageFor(planMode, categoryIds().indexOf(id))
            return pendingPage != null
        }
    })
    addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val doubleTap = detector.onTouchEvent(e) && pendingPage != null
            // ViewPager2 snaps to the finger on the way up if it still owns the gesture.
            if (doubleTap) rv.parent?.requestDisallowInterceptTouchEvent(true)
            return doubleTap
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            if (e.actionMasked != MotionEvent.ACTION_UP && e.actionMasked != MotionEvent.ACTION_CANCEL) return
            pendingPage?.let(openPage)
            pendingPage = null
        }
    })
}
