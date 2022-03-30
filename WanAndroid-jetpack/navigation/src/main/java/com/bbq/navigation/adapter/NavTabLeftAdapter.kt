package com.bbq.navigation.adapter

import android.text.Html
import com.bbq.base.utils.getResColor
import com.bbq.navigation.R
import com.bbq.navigation.bean.NavTabBean
import com.bbq.navigation.databinding.NavItemNavLeftBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 左边导航列表适配器
 * @constructor
 * TODO
 *
 * @param list 数据列表
 */
class NavTabLeftAdapter(list: MutableList<NavTabBean>?) :
    BaseQuickAdapter<NavTabBean, BaseDataBindingHolder<NavItemNavLeftBinding>>(
        R.layout.nav_item_nav_left, list
    ) {

    /**
     * 绑定数据
     * @param holder 视图
     * @param item 数据
     */
    override fun convert(holder: BaseDataBindingHolder<NavItemNavLeftBinding>, item: NavTabBean) {
        holder.dataBinding?.tvLeftTxt?.text = Html.fromHtml(item.name)
        val bgColor =
            if (item.isSelected) R.color.white.getResColor() else R.color.colorWhiteDark.getResColor()
        //设置选中、未选中效果
        holder.dataBinding?.tvLeftTxt?.setBackgroundColor(bgColor)
    }

}