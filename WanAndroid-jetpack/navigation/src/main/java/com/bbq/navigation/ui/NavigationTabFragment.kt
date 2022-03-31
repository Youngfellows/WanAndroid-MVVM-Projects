package com.bbq.navigation.ui

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bbq.base.base.BaseVMFragment
import com.bbq.base.loadsir.EmptyCallback
import com.bbq.base.loadsir.ErrorCallback
import com.bbq.base.route.WebServiceUtils
import com.bbq.navigation.R
import com.bbq.navigation.adapter.NavTabLeftAdapter
import com.bbq.navigation.adapter.NavTabRightAdapter
import com.bbq.navigation.bean.ArticleBean
import com.bbq.navigation.bean.NavTabBean
import com.bbq.navigation.databinding.NavFragmentNavigationTabBinding
import com.bbq.navigation.viewmodel.NavTabVM
import com.bbq.net.model.DataStatus
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 导航TAB页面
 */
class NavigationTabFragment : BaseVMFragment<NavFragmentNavigationTabBinding>() {

    /**
     * 导航页关联被观察数据的ViewModel
     */
    private val viewModel: NavTabVM by viewModel()

    /**
     * 左边导航列表
     */
    private val mLeftList by lazy {
        mutableListOf<NavTabBean>()
    }

    /**
     * 右边文章列表
     */
    private val mRightList by lazy {
        mutableListOf<ArticleBean>()
    }

    /**
     * 左边导航列表适配器
     */
    private val mLeftAdapter by lazy {
        NavTabLeftAdapter(mLeftList)
    }


    /**
     * 右边文章列表适配器
     */
    private val mRightAdapter by lazy {
        NavTabRightAdapter(mRightList)
    }

    /**
     * 加载反馈
     */
    private val mLoadSir by lazy {
        val loadSir = LoadSir.Builder()
            .addCallback(EmptyCallback())
            .addCallback(ErrorCallback())
            .setDefaultCallback(ErrorCallback::class.java)
            .build()

        loadSir.register(mBinding.root) {
            viewModel.getNavList()
        }
    }

    /**
     * 当前选中的左边导航数据实体
     */
    private lateinit var mCurrentNavTab: NavTabBean

    override fun initView(view: View) {
        mBinding.recyclerLeft.layoutManager = LinearLayoutManager(requireContext())
        mBinding.recyclerLeft.adapter = mLeftAdapter

        mBinding.recyclerRight.layoutManager = LinearLayoutManager(requireContext())
        mBinding.recyclerRight.adapter = mRightAdapter

        mLeftAdapter.setOnItemClickListener { adapter, view, position ->
            if (mLeftList[position].cid == mCurrentNavTab.cid) return@setOnItemClickListener
            //选中当前的
            mLeftList.forEachIndexed { index, navTabBean ->
                //选中
                if (index == position) {
                    navTabBean.isSelected = true
                    mCurrentNavTab = navTabBean
                    switchRightData()
                } else {
                    //未选中
                    navTabBean.isSelected = false
                }
                //刷新列表
                mLeftAdapter.notifyDataSetChanged()
            }
        }
        mRightAdapter.setOnItemClickListener { adapter, view, position ->
            val bean = mRightList[position]
            //点击跳转到网页
            WebServiceUtils.goWeb(requireContext(), bean.title, bean.link)
        }

    }

    override fun initData() {
        super.initData()
        viewModel.getNavList()
    }

    override fun startObserver() {
        super.startObserver()
        viewModel.mLeftList.observe(this, {
            when (it.dataStatus) {
                DataStatus.STATE_LOADING -> {
                    showLoading()
                }
                DataStatus.STATE_ERROR -> {
                    dismissLoading()
//                    it?.exception?.msg?.showToast()
                    mLoadSir.showCallback(ErrorCallback::class.java)
                }
                DataStatus.STATE_SUCCESS -> {
                    dismissLoading()
                    if (it.data.isNullOrEmpty()) {
//                        toast("数据为空!")
                        mLoadSir.showCallback(EmptyCallback::class.java)
                        return@observe
                    }
                    mLeftList.clear()
                    //默认选中第一个
                    it.data!![0].isSelected = true
                    mCurrentNavTab = it.data!![0]
                    //加入list
                    mLeftList.addAll(it.data!!)
                    mLeftAdapter.notifyDataSetChanged()
                    switchRightData()
                }
            }
        })
    }

    private fun switchRightData() {
        mRightList.clear()
        if (!mCurrentNavTab.articles.isNullOrEmpty()) {
            mRightList.addAll(mCurrentNavTab.articles!!)
        }
        mRightAdapter.notifyDataSetChanged()
    }

    override fun getLayoutId(): Int {
        return R.layout.nav_fragment_navigation_tab
    }
}