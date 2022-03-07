package com.bbq.home.adapter

import com.bbq.home.bean.BannerBean
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder


/**
 * 轮播图的Adapter适配器
 * @constructor
 *
 * @param mData 数据集
 */
class BannerAdapter(mData: MutableList<BannerBean>?) : BannerImageAdapter<BannerBean>(mData) {

    /**
     * 绑定数据
     * @param holder 视图的ViewHolder
     * @param data 条目数据
     * @param position 位置
     * @param size
     */
    override fun onBindView(
        holder: BannerImageHolder,
        data: BannerBean?,
        position: Int,
        size: Int
    ) {
        //图片加载自己实现
        Glide.with(holder.itemView)
            .load(data?.imagePath)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(holder.imageView);

    }
}