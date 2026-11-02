package com.mmgpt.shoplistgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Double-tapping a category must open that category's page.
 * Plan keeps the category list on page 0, so category i is page i + 1.
 * Shop keeps the summary on the last page, so category i is page i.
 * The tap is resolved by category id, because a row position is not the
 * category index: Plan inserts an add-row above the names, and a reorder
 * changes which row a name sits on.
 */
class CategoryNavigationTest {

    @Test
    fun planMode_firstCategory_opensThePageAfterTheList() {
        assertEquals(1, CategoryNavigation.pageFor(planMode = true, categoryIndex = 0))
    }

    @Test
    fun planMode_laterCategory_skipsTheListPage() {
        assertEquals(4, CategoryNavigation.pageFor(planMode = true, categoryIndex = 3))
    }

    @Test
    fun shopMode_usesTheCategoryIndexAsThePage() {
        assertEquals(0, CategoryNavigation.pageFor(planMode = false, categoryIndex = 0))
        assertEquals(3, CategoryNavigation.pageFor(planMode = false, categoryIndex = 3))
    }

    @Test
    fun unknownCategory_doesNotChangePage() {
        assertNull(CategoryNavigation.pageFor(planMode = true, categoryIndex = -1))
        assertNull(CategoryNavigation.pageFor(planMode = false, categoryIndex = -1))
    }

    @Test
    fun planRow_isNotTheCategoryIndex() {
        // Row 0 is the add field, so Bakery (id 20) is row 2 but category 1, page 2.
        val ids = listOf(10L, 20L, 30L)
        val index = CategoryNavigation.indexOfId(ids, tappedId = 20L)
        assertEquals(1, index)
        assertEquals(2, CategoryNavigation.pageFor(planMode = true, categoryIndex = index))
    }

    @Test
    fun shopRow_pageFollowsTheCategoryId_notAnOlderPosition() {
        // After a reorder, Dairy (id 10) is the middle category and must open page 1.
        val ids = listOf(30L, 10L, 20L)
        val index = CategoryNavigation.indexOfId(ids, tappedId = 10L)
        assertEquals(1, index)
        assertEquals(1, CategoryNavigation.pageFor(planMode = false, categoryIndex = index))
    }

    @Test
    fun missingId_hasNoPage() {
        assertEquals(-1, CategoryNavigation.indexOfId(listOf(10L, 20L), tappedId = 99L))
        assertNull(CategoryNavigation.pageFor(planMode = true, categoryIndex = -1))
    }
}
