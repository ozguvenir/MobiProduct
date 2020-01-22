package com.ridvan.mobiproduct.utilities

import com.ridvan.mobiproduct.model.Category
import com.ridvan.mobiproduct.model.CategoryAndProduct

object TypeConverter {

    fun getCategoryAndProductList(
        categories: List<Category>,
        productTypeDataList: MutableList<CategoryAndProduct>
    ): MutableList<CategoryAndProduct> {
        return productTypeDataList.apply {
            clear()
            categories.forEach {
                add(CategoryAndProduct(ItemType.CATEGORY, categoryName = it.name))
                addAll(it.products.map { product ->
                    CategoryAndProduct(ItemType.PRODUCT, product = product)
                }.toMutableList())
            }

        }
    }
}