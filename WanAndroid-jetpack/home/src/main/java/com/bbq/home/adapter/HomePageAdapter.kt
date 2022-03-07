package com.bbq.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bbq.base.route.WebService
import com.bbq.home.R
import com.bbq.home.bean.ArticleBean
import com.bbq.home.bean.BannerBean
import com.bbq.home.databinding.ItemHomeBinding
import com.bbq.home.viewmodel.ItemHomeArticle
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.DepthPageTransformer

class HomePageAdapter(val context: Context) :
    PagingDataAdapter<ArticleBean, RecyclerView.ViewHolder>(differCallback) {

    /**
     * 初始化轮播图Adapter适配器
     */
    private val mBannerAdapter by lazy {
        BannerAdapter(null)
    }

    /**
     * 伴生类,静态属性,静态方法
     */
    companion object {

        /**
         * 轮播图类型
         */
        const val TYPE_BANNER = 0

        /**
         * 文章类型
         */
        const val TYPE_ARTICLE = 1

        /**
         * 静态属性,匿名对象
         */
        val differCallback = object : DiffUtil.ItemCallback<ArticleBean>() {
            override fun areItemsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
                return oldItem == newItem
            }
        }
    }

    /**
     * 获取指定位置的item类型
     * @param position 指定的位置
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        //0变成了banner，所以后面的数据要 -1
        return if (position == 0) {
            TYPE_BANNER
        } else {
            TYPE_ARTICLE
        }
    }

    /**
     * 首页文章列表ViewHolder
     * @property dataBinding
     */
    inner class HomeViewHolder(val dataBinding: ItemHomeBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
    }

    /**
     * 轮播图的ViewHolder-内部类
     * @constructor
     *
     * @param itemView 视图
     */
    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            val banner =
                itemView.findViewById<Banner<BannerBean, BannerImageAdapter<BannerBean>>>(R.id.banner)
            banner.apply {
                setAdapter(mBannerAdapter)
                indicator = CircleIndicator(itemView.context)
                addPageTransformer(DepthPageTransformer())
            }
            //banner点击事件
            banner.setOnBannerListener { data, position ->
                ARouter.getInstance().navigation(WebService::class.java)
                    .goWeb(context = context, data.title, data.url, -1, false)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TYPE_BANNER) {
        } else {
            val holder = holder as HomeViewHolder
            val article = ItemHomeArticle(getItem(position - 1)!!)
            article.bindData()
            holder.dataBinding?.item = article
            holder.itemView.setOnClickListener {
                ARouter.getInstance().navigation(WebService::class.java)
                    .goWeb(
                        context, getItem(position - 1)!!.title,
                        getItem(position - 1)!!.link!!,
                        getItem(position - 1)!!.id,
                        getItem(position - 1)!!.collect
                    )
            }
            holder.dataBinding.tvCollect.setOnClickListener {
                if (collectListener != null) {
                    collectListener!!.collect(article, position)
                }
            }
        }
    }

    /**
     * 为视图创建ViewHolder
     * @param parent
     * @param viewType item视图类型
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_BANNER) {
            val bannerView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_head_banner, parent, false)
            BannerViewHolder(bannerView)
        } else {
            HomeViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_home,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    fun getItemData(position: Int): ArticleBean? {
        return getItem(position)
    }


    fun setBannerList(bannerList: List<BannerBean>) {
        mBannerAdapter.setDatas(bannerList)
    }

    private var collectListener: OnCollectListener? = null
    fun setOnCollectListener(listener: OnCollectListener) {
        this.collectListener = listener
    }

    interface OnCollectListener {
        fun collect(articleBean: ItemHomeArticle, position: Int)
    }

}