package com.mmgpt.shoplistgpt

object CategoryNavigation {
    /** Plan keeps the category list on page 0. Shop does not. */
    fun pageFor(planMode: Boolean, categoryIndex: Int): Int? {
        if (categoryIndex < 0) return null
        return if (planMode) categoryIndex + 1 else categoryIndex
    }
}
