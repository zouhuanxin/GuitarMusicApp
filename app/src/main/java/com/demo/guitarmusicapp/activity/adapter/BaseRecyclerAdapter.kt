package com.demo.guitarmusicapp.activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class BaseRecyclerAdapter<M>(
        @LayoutRes val itemLayoutId: Int, list: Collection<M>? = null,
        bind: (BaseRecyclerAdapter<M>.() -> Unit)? = null
) : RecyclerView.Adapter<BaseRecyclerAdapter.CommonViewHolder>() {

    init {
        if (bind != null) {
            apply(bind)
        }
    }

    private var dataList = mutableListOf<M>()

    private var mOnItemClickListener: ((v: View, position: Int) -> Unit)? = null
    private var mOnItemLongClickListener: ((v: View, position: Int) -> Boolean) = { _, _ -> false }

    private var onBindViewHolder: ((holder: CommonViewHolder, position: Int) -> Unit)? = null

    fun onBindViewHolder(onBindViewHolder: ((holder: CommonViewHolder, position: Int) -> Unit)) {
        this.onBindViewHolder = onBindViewHolder
    }

    /**
     * 填充数据,此操作会清除原来的数据
     *
     * @param list 要填充的数据
     * @return true:填充成功并调用刷新数据
     */
    fun setData(list: Collection<M>?): Boolean {
        var result = false
        dataList.clear()
        if (list != null) {
            result = dataList.addAll(list)
        }
        return result
    }

    /**
     * 根据位置获取一条数据
     *
     * @param position View的位置
     * @return 数据
     */
    fun getItem(position: Int) = dataList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)
        val viewHolder = CommonViewHolder(itemView)
        itemView.setOnClickListener { mOnItemClickListener?.invoke(it, viewHolder.adapterPosition) }
        itemView.setOnLongClickListener { return@setOnLongClickListener mOnItemLongClickListener.invoke(it, viewHolder.adapterPosition) }
        return viewHolder
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        if (onBindViewHolder != null) {
            onBindViewHolder!!.invoke(holder, position)
        } else {
            bindData(holder, position)
        }
    }

    open fun bindData(holder: CommonViewHolder, position: Int) {

    }

    class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
        override val containerView: View = itemView
    }


}