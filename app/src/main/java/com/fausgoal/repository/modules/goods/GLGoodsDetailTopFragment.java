package com.fausgoal.repository.modules.goods;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fausgoal.repository.R;
import com.fausgoal.repository.base.GLParentFragment;
import com.fausgoal.repository.callback.IPosterWidthAndHeightCallback;
import com.fausgoal.repository.utils.GLPixelsUtil;

/**
 * Description：商品详情上部分内容
 * <br/><br/>Created by Fausgoal on 4/25/17.
 * <br/><br/>
 */
public class GLGoodsDetailTopFragment extends GLParentFragment {

    public static GLGoodsDetailTopFragment newInstance() {
        return new GLGoodsDetailTopFragment();
    }

    public interface OnTopScrollChangeListener {
        void onScrollChange(int scrollY);
    }

    private OnTopScrollChangeListener mScrollChangeListener = null;
    private IPosterWidthAndHeightCallback mPoseterCallback = null;

    private View mView = null;
    private NestedScrollView svScroll = null;

    public void setOnScrollChangeListener(OnTopScrollChangeListener scrollChangeListener) {
        mScrollChangeListener = scrollChangeListener;
    }

    public void setPoseterCallback(IPosterWidthAndHeightCallback poseterCallback) {
        mPoseterCallback = poseterCallback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fr_goods_detail_top, container, false);
        return mView;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        svScroll = findView(mView, R.id.svScroll);
        ImageView ivGoodsImg = findView(mView, R.id.ivGoodsImg);

        // 计算海报显示的宽高
        int posterWidth = GLPixelsUtil.getScreenWidth();
        int posterHeight = (int) (1.0f * posterWidth / 1.6f);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivGoodsImg.getLayoutParams();
        params.width = posterWidth;
        params.height = posterHeight;
        ivGoodsImg.setLayoutParams(params);

        if (null != mPoseterCallback) {
            mPoseterCallback.onPoserHeight(posterWidth, posterHeight);
        }
        loadIngData();
    }

    public void loadIngData() {

    }

    @Override
    protected void setListener() {
        svScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // 设置ScrollView的滚动监听，改变导航的动画
                if (null != mScrollChangeListener) {
                    mScrollChangeListener.onScrollChange(v.getScrollY());
                }
            }
        });
    }
}
