package com.bbq.home.adapter

import com.bbq.home.R
import com.bbq.home.bean.HotKeyBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 热词列表适配器
 * @constructor
 * TODO
 *
 * @param list 数据集
 */
class HotKeyAdapter(list: MutableList<HotKeyBean>?) :
    BaseQuickAdapter<HotKeyBean, BaseViewHolder>(R.layout.item_hot_key, list) {

    /**
     * 绑定每个item数据
     * @param holder 视图
     * @param item 条目数据实体
     */
    override fun convert(holder: BaseViewHolder, item: HotKeyBean) {
        holder.setText(R.id.hot_key, item.name)
    }
}