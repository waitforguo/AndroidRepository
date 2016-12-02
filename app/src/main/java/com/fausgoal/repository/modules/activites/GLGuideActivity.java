package com.fausgoal.repository.modules.activites;

import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.fausgoal.repository.R;
import com.fausgoal.repository.base.GLParentActivity;
import com.fausgoal.repository.callback.IGLOnListItemClickListener;
import com.fausgoal.repository.common.GLCommonVariables;
import com.fausgoal.repository.common.GLConst;
import com.fausgoal.repository.common.GLViewManager;
import com.fausgoal.repository.modules.adapters.GLGuideAdapter;
import com.fausgoal.repository.utils.GLResourcesUtil;
import com.viewpagerindicator.GuideCirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：引导页适配器
 * <br/><br/>Created by Fausgoal on 2016/12/2.
 * <br/><br/>
 */
public class GLGuideActivity extends GLParentActivity implements ViewPager.OnPageChangeListener, IGLOnListItemClickListener {

    private ViewPager vpPager = null;
    private GuideCirclePageIndicator pageIndicator = null;

    private GLGuideAdapter mAdapter = null;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_guide);

        vpPager = findView(R.id.lead_viewPager);
        pageIndicator = findView(R.id.pageIndicator);

        List<Integer> guides = new ArrayList<>();
        guides.add(R.mipmap.g1);
        guides.add(R.mipmap.g2);
        guides.add(R.mipmap.g3);
        guides.add(R.mipmap.g4);
        guides.add(R.mipmap.g5);

        mAdapter = new GLGuideAdapter(this, guides, this);
        vpPager.setAdapter(mAdapter);
        pageIndicator.setViewPager(vpPager, 0);

        vpPager.addOnPageChangeListener(this);
        pageIndicator.setFillColor(GLResourcesUtil.getColor(R.color.white));
    }

    @Override
    public void onClickItem(int position, View v) {
        int count = mAdapter.getCount();
        if (count - GLConst.ONE == position) {
            GLCommonVariables.getIns().saveObject(GLCommonVariables.Keys.IS_FIRST_START, false);
            // 到主界面
            GLViewManager.getIns().showActivity(mContext, GLMainActivity.class, true);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pageIndicator.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (null != vpPager) {
            vpPager.removeOnPageChangeListener(this);
        }
        super.onDestroy();
    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected boolean hasSwipeBackLayout() {
        return false;
    }
}
