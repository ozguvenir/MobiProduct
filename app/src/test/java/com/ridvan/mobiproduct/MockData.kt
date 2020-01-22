package com.ridvan.mobiproduct

import com.ridvan.mobiproduct.model.CategoryAndProduct
import com.ridvan.mobiproduct.model.Product
import com.ridvan.mobiproduct.model.SalePrice
import com.ridvan.mobiproduct.utilities.ItemType

object MockData {

    fun createCategoryAnProductList(): List<CategoryAndProduct> {
        return listOf(
            CategoryAndProduct(
                ItemType.CATEGORY,
                null,
                "Category1"
            ), CategoryAndProduct(
                ItemType.PRODUCT,
                Product("1", "Food", "Product1", "", "", SalePrice("100.0", "EUR")),
                "Category1"
            ), CategoryAndProduct(
                ItemType.PRODUCT,
                Product("2", "Food", "Product2", "", "", SalePrice("40.0", "EUR")),
                "Category1"
            ), CategoryAndProduct(
                ItemType.CATEGORY,
                null,
                "Category2"
            ), CategoryAndProduct(
                ItemType.PRODUCT,
                Product("3", "Drink", "Product3", "", "", SalePrice("20.0", "EUR")),
                "Category2"
            ), CategoryAndProduct(
                ItemType.PRODUCT,
                Product("4", "Drink", "Product4", "", "", SalePrice("50.0", "EUR")),
                "Category2"
            )
        )
    }
}
