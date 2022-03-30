package com.bbq.navigation.adapter

import com.bbq.base.utils.getHtmlStr
import com.bbq.base.utils.getRandomColor
import com.bbq.navigation.R
import com.bbq.navigation.bean.ArticleBean
import com.bbq.navigation.databinding.NavItemNavRightBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 右边文章列表适配器
 * @property list 数据列表
 */
class NavTabRightAdapter(val list: MutableList<ArticleBean>?) :
    BaseQuickAdapter<ArticleBean, BaseDataBindingHolder<NavItemNavRightBinding>>(
        R.layout.nav_item_nav_right,
        list
    ) {

    /**
     * 绑定数据
     * @param holder 视图
     * @param item 数据
     */
    override fun convert(holder: BaseDataBindingHolder<NavItemNavRightBinding>, item: ArticleBean) {
        holder.dataBinding?.tvRightTxt?.text = item.title.getHtmlStr()
        holder.dataBinding?.cardView?.setBackgroundColor(getRandomColor())
    }
}