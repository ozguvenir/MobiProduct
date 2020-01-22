package com.ridvan.mobiproduct.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ridvan.mobiproduct.R
import com.ridvan.mobiproduct.databinding.ItemCategoryTitleBinding
import com.ridvan.mobiproduct.databinding.ItemProductListBinding
import com.ridvan.mobiproduct.model.CategoryAndProduct
import com.ridvan.mobiproduct.utilities.ItemType
import com.ridvan.mobiproduct.view.callback.ItemClickCallback

class ProductListAdapter(
    @Nullable private val itemClickCallback: ItemClickCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val CATEGORY_VIEW = 0
    private val PRODUCT_VIEW = 1

    private val items = mutableListOf<CategoryAndProduct>()

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            ItemType.PRODUCT -> PRODUCT_VIEW
            ItemType.CATEGORY -> CATEGORY_VIEW
        }
    }

    class ProductViewHolder
        (internal val binding: ItemProductListBinding) : RecyclerView.ViewHolder(binding.root)

    class CategoryViewHolder(internal val binding: ItemCategoryTitleBinding) :
        RecyclerView.ViewHolder(binding.root)

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == CATEGORY_VIEW) {
            val binding: ItemCategoryTitleBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_category_title,
                    parent,
                    false
                )
            CategoryViewHolder(binding)
        } else {
            val binding: ItemProductListBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_product_list,
                    parent,
                    false
                )
            binding.callback = itemClickCallback
            ProductViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (item.type == ItemType.CATEGORY) {
            val categoryHolder = holder as CategoryViewHolder
            item.categoryName?.let {
                categoryHolder.binding.category = it
                categoryHolder.binding.executePendingBindings()
            }
        } else {
            val productHolder = holder as ProductViewHolder
            item.product?.let {
                productHolder.binding.product = it
                productHolder.binding.executePendingBindings()
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateCategoryAndProductList(data: MutableList<CategoryAndProduct>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }
}