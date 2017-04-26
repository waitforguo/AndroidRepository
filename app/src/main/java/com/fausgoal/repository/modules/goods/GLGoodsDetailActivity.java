package com.fausgoal.repository.modules.goods;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fausgoal.repository.R;
import com.fausgoal.repository.base.GLParentActivity;
import com.fausgoal.repository.callback.IPosterWidthAndHeightCallback;
import com.fausgoal.repository.common.GLConst;
import com.fausgoal.repository.utils.GLListUtil;
import com.fausgoal.repository.utils.GLResourcesUtil;
import com.fausgoal.repository.widget.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：商品详情页的Demo
 * <br/><br/>Created by Fausgoal on 4/25/17.
 * <br/><br/>
 */
public class GLGoodsDetailActivity extends GLParentActivity implements ViewPager.OnPageChangeListener,
        IPosterWidthAndHeightCallback, GLGoodsDetailTopFragment.OnTopScrollChangeListener {
    private VerticalViewPager vpPager = null;

    private RelativeLayout rlTop = null;
    private View viewNavLine = null;
    private RelativeLayout rlLeftLayout = null;
    private ImageView ivLeft = null;
    private LinearLayout llTitle = null;
    private TextView tvNavTitle = null;
    private RelativeLayout rlRightImgAndText = null;
    private ImageView ivRightImg = null;

    private List<Fragment> mFragments = null;
    private GLGoodsDetailTopFragment mTopFragment = null;
    private GLGoodsDetailBottomFragment mBottomFragment = null;

    private int mDistanceY = GLConst.NONE;
    /**
     * 海报的高度
     */
    private int mPosterHeight = GLConst.NONE;
    /**
     * navbar的高度
     */
    private int mNavbarHeight = GLConst.NONE;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.act_goods_detail);

        vpPager = findView(R.id.vpPager);

        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
        }
        toolbar.setBackgroundColor(GLResourcesUtil.getColor(R.color.transparent));

        rlTop = findView(R.id.rlTop);
        viewNavLine = findView(R.id.viewNavLine);
        rlLeftLayout = findView(R.id.rlLeftLayout);
        ivLeft = findView(R.id.ivLeft);
        llTitle = findView(R.id.llTitle);
        tvNavTitle = findView(R.id.tvNavTitle);
        rlRightImgAndText = findView(R.id.rlRightImgAndText);
        ivRightImg = findView(R.id.ivRightImg);

        initHeader();
        initViewPager();
    }

    private void initHeader() {
        rlLeftLayout.setVisibility(View.VISIBLE);
        ivLeft.setVisibility(View.VISIBLE);
        llTitle.setVisibility(View.GONE);
        rlRightImgAndText.setVisibility(View.VISIBLE);
        viewNavLine.setVisibility(View.GONE);
        setLeftIcon(R.mipmap.icon_back_round_detail, R.mipmap.icon_share_round);
        tvNavTitle.setText("商品详情");
        setNavBarBgcolor(0);
        showNavbarViewAlpha(1.0f);
    }

    private void setLeftIcon(int leftIcon, int rightIcon) {
        ivLeft.setImageResource(leftIcon);
        ivRightImg.setImageResource(rightIcon);
    }

    /**
     * 滚动时渐变导航的背景色
     */
    private void setNavBarBgcolor(int alpha) {
        if (null != rlTop) {
            // 更改navbar的白色透明度,
            rlTop.setBackgroundColor(Color.argb(alpha, 255, 255, 255)); // 白色
        }
    }

    private void showNavbarViewAlpha(float alpha) {
        ivLeft.setAlpha(alpha);
        llTitle.setAlpha(alpha);
        tvNavTitle.setAlpha(alpha);
        rlRightImgAndText.setAlpha(alpha);
        ivRightImg.setAlpha(alpha);
    }

    private void setNormalNavbar() {
        setNavBarBgcolor(255);
        setLeftIcon(R.mipmap.icon_back, R.mipmap.icon_share);
        llTitle.setVisibility(View.VISIBLE);
        viewNavLine.setVisibility(View.VISIBLE);
        showNavbarViewAlpha(1.0f);
    }

    /**
     * 滑动ScrollView计算滑动距离改变导航栏显示样式
     *
     * @param dy 滑动距离
     */
    private void setNavbarAmin(int dy) {
        mDistanceY = dy;
        if (mNavbarHeight == GLConst.NONE && null != rlTop) {
            mNavbarHeight = rlTop.getBottom();
        }
        if (dy <= GLConst.NONE) {
            setNavBarBgcolor(0);
            llTitle.setVisibility(View.GONE);
            viewNavLine.setVisibility(View.GONE);
            setLeftIcon(R.mipmap.icon_back_round_detail, R.mipmap.icon_share_round);
            showNavbarViewAlpha(1.0f);
        }
        //当滑动的距离 <= 海报高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
        else if ((dy + mNavbarHeight) <= mPosterHeight) {
            float scale = (1.0f * (dy + mNavbarHeight)) / mPosterHeight;
            float alpha = scale * 255;
            setNavBarBgcolor((int) alpha);
            // 滑动到海报的一半后要改变消息图标
            if (dy <= (mPosterHeight / 2)) {
                setLeftIcon(R.mipmap.icon_back_round_detail, R.mipmap.icon_share_round);
                llTitle.setVisibility(View.GONE);
                viewNavLine.setVisibility(View.GONE);
            } else {
                setLeftIcon(R.mipmap.icon_back, R.mipmap.icon_share);
                llTitle.setVisibility(View.VISIBLE);
                viewNavLine.setVisibility(View.VISIBLE);
            }
            float msgIconAlpha = 1 - (1.0f * dy / mPosterHeight / 2);// 0~1  透明度是1~0
            showNavbarViewAlpha(msgIconAlpha);
        } else {
            //上述虽然判断了滑动距离与toolbar高度相等的情况，但是实际测试时发现，标题栏的背景色
            //很少能达到完全不透明的情况，所以这里又判断了滑动距离大于toolbar高度的情况，
            //将标题栏的颜色设置为完全不透明状态
            setNormalNavbar();
        }
    }

    private void initFragments() {
        if (null == mFragments) {
            mFragments = new ArrayList<>();
        } else {
            mFragments.clear();
        }

        mTopFragment = GLGoodsDetailTopFragment.newInstance();
        mTopFragment.setOnScrollChangeListener(this);
        mTopFragment.setPoseterCallback(this);
        mBottomFragment = GLGoodsDetailBottomFragment.newInstance();
        mFragments.add(mTopFragment);
        mFragments.add(mBottomFragment);
    }

    private void initViewPager() {
        initFragments();
        vpPager.setOffscreenPageLimit(mFragments.size());
        vpPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return GLListUtil.getSize(mFragments);
            }
        });
        vpPager.setOnPageChangeListener(this);
    }

    @Override
    public void onScrollChange(int scrollY) {
        setNavbarAmin(scrollY);
    }

    @Override
    public void onPoserHeight(int width, int height) {
        mPosterHeight = height;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 1) {
            // TODO: 4/25/17 可以在滑动到第二页的时候去加载第二页的数据
            if (null != mBottomFragment) {
                mBottomFragment.loadIngData();
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }
}
