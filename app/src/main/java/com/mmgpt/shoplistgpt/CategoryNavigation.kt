package com.mmgpt.shoplistgpt

/**
 * Which ViewPager page a category opens.
 *
 * Plan mode's first page is the category list, so category 0 is page 1.
 * Shop mode's category pages come first and the summary is last, so
 * category 0 is page 0.
 */
object CategoryNavigation {
    fun indexOfId(categoryIds: List<Long>, tappedId: Long): Int = categoryIds.indexOf(tappedId)

    fun pageFor(planMode: Boolean, categoryIndex: Int): Int? {
        if (categoryIndex < 0) return null
        return if (planMode) categoryIndex + 1 else categoryIndex
    }
}
