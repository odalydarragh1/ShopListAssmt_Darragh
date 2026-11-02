package com.mmgpt.shoplistgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

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
}
