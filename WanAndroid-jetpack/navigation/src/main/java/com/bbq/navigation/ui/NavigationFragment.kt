package com.bbq.navigation.ui

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bbq.base.base.BaseVMFragment
import com.bbq.navigation.R
import com.bbq.navigation.databinding.NavFragmentNavigationBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * 导航页
 */
class NavigationFragment : BaseVMFragment<NavFragmentNavigationBinding>() {

    /**
     * title列表
     */
    private val mTitles = arrayOf("导航", "体系", "公众号", "项目", "项目分类")

    /**
     * TAB轮播页面列表
     */
    private val mFragmentList by lazy {
        val list = mutableListOf<Fragment>()
        list.add(NavigationTabFragment())
        list.add(SystemTabFragment())
        list.add(PublicTabFragment())
        list.add(ProjectTabFragment())
        list.add(ProjectTreeTabFragment())
        list
    }

    override fun getLayoutId(): Int {
        return R.layout.nav_fragment_navigation
    }

    override fun initView(view: View) {
        //设置轮播页适配器
        mBinding.viewPager2.adapter =
            object : FragmentStateAdapter(childFragmentManager, lifecycle) {
                override fun getItemCount(): Int {
                    return mFragmentList.size
                }

                override fun createFragment(position: Int): Fragment {
                    return mFragmentList[position]
                }

            }
        //关联ViewPager与TabLayout
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager2) { tab, position ->
            tab.text = mTitles[position]
        }.attach()
        mBinding.viewPager2.offscreenPageLimit = 5
    }
}