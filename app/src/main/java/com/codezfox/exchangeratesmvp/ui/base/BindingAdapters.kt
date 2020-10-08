package com.codezfox.exchangeratesmvp.ui.base

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.view.View
import com.codezfox.exchangeratesmvp.ui.base.adapter.DisplayableItem
import com.codezfox.exchangeratesmvp.ui.base.adapter.MultiAdapter

object BindingAdapters {

    @BindingAdapter("app:visibleGone")
    @JvmStatic
    fun setVisibilityGone(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("app:visibleInvisible")
    @JvmStatic
    fun setVisibilityInvisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    @BindingAdapter(value = ["app:items", "app:adapter"])
    @JvmStatic
    fun setItems(view: RecyclerView, items: List<DisplayableItem>?, adapter: MultiAdapter) {
        if (view.adapter != adapter) {
            view.adapter = adapter
        }
        view.adapter?.also {
//            view.recycledViewPool.clear()
            (it as MultiAdapter).submitList(items)
        }
    }
//
//    @BindingAdapter(value = ["app:items", "app:adapter", "app:isShowShimmer", "app:shimmerAdapter", "app:shimmerItems"], requireAll = false)
//    @JvmStatic
//    fun setItems(view: RecyclerView, items: List<Any>?, adapter: RecyclerView.Adapter<*>, isShowShimmer: Boolean?, shimmerAdapter: RecyclerView.Adapter<*>?, shimmerItems: List<ShimmerItem>?) {
//        if (isShowShimmer != null && isShowShimmer == true) {
//            if (view.adapter != shimmerAdapter) {
//                view.adapter = shimmerAdapter
//            }
//            if (shimmerItems != null) {
//                view.adapter?.also {
//                    view.recycledViewPool.clear()
//                    (it as ListAdapter<Any, *>).submitList(shimmerItems)
//                }
//            }
//        } else {
//            if (view.adapter != adapter) {
//                view.adapter = adapter
//            }
//            if (items != null) {
//                view.adapter?.also {
//                    view.recycledViewPool.clear()
//                    (it as ListAdapter<Any, *>).submitList(ArrayList(items))
//                }
//            }
//        }
//    }


}