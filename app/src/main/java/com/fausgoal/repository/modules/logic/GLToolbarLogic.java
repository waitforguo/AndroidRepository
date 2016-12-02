package com.fausgoal.repository.modules.logic;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fausgoal.repository.R;
import com.fausgoal.repository.common.GLConst;
import com.fausgoal.repository.utils.GLPixelsUtil;
import com.fausgoal.repository.utils.GLResourcesUtil;
import com.fausgoal.repository.utils.GLStringUtil;
import com.fausgoal.repository.utils.GLViewClickUtil;


/**
 * Description：导航toolbar逻辑处理类
 * <br/><br/>Created by Fausgoal on 16/1/11.
 * <br/><br/>
 */
public class GLToolbarLogic implements GLViewClickUtil.NoFastClickListener {
    public static final int VIEW_LEFT_CLICK = 10001;
    public static final int VIEW_CENTER_CLICK = 10002;
    public static final int VIEW_RIGHT_CLICK = 10003;
    public static final int VIEW_RIGHT_IMG_AND_TEXT = 10004;
    public static final int VIEW_RIGHT_TEXT_CLICK = 10005;
    public static final int VIEW_LEFT_TEXT_CLICK = 10006;

    /**
     * 点击提示
     */
    public static final int VIEW_TIPS_CLICK = 1001;

    private static final int DEFAULT_MAX_WIDTH = 250;

    private final Activity mActivity;
    private final Point mPoint;

    private LinearLayout llToolbarParent = null;
    private Toolbar mToolbar = null;
    private RelativeLayout rlLeftLayout = null;
    private ImageView ivLeft = null;
    private TextView tvLeftCount = null;
    private TextView tvLeftText = null;
    private LinearLayout llTitle = null;
    private TextView tvNavTitle = null;
    private ImageView ivTitleImg = null;
    private RelativeLayout rlRightLayout = null;
    private ImageView ivRight = null;
    private TextView tvRightCount = null;

    private RelativeLayout rlRightImgAndText = null;
    private ImageView ivRightImg = null;
    private TextView tvRightImgText = null;

    private TextView tvRightText = null;
    private View viewTips = null;

    private IToolbarViewClickListener mToolbarViewClickListener = null;

    public void setToolbarClickListener(IToolbarViewClickListener toolbarViewClickListener) {
        mToolbarViewClickListener = toolbarViewClickListener;
    }

    public GLToolbarLogic(Activity activity) {
        mActivity = activity;
        mPoint = GLPixelsUtil.getDeviceSize();

        if (activity instanceof AppCompatActivity) {
            llToolbarParent = findView(R.id.llToolbarParent);
            mToolbar = findView(R.id.toolbar);
            AppCompatActivity appCompatActivity = ((AppCompatActivity) mActivity);
            if (null != mToolbar) {
                appCompatActivity.setSupportActionBar(mToolbar);
                ActionBar actionBar = appCompatActivity.getSupportActionBar();
                if (null != actionBar) {
                    actionBar.setDisplayShowTitleEnabled(false);
                    actionBar.setDisplayUseLogoEnabled(false);
                }
            }

            rlLeftLayout = findView(R.id.rlLeftLayout);
            ivLeft = findView(R.id.ivLeft);
            tvLeftCount = findView(R.id.tvLeftCount);
            tvLeftText = findView(R.id.tvLeftText);
            llTitle = findView(R.id.llTitle);
            tvNavTitle = findView(R.id.tvNavTitle);
            ivTitleImg = findView(R.id.ivTitleImg);
            rlRightLayout = findView(R.id.rlRightLayout);
            ivRight = findView(R.id.ivRight);
            tvRightCount = findView(R.id.tvRightCount);
            rlRightImgAndText = findView(R.id.rlRightImgAndText);
            ivRightImg = findView(R.id.ivRightImg);
            tvRightImgText = findView(R.id.tvRightImgText);
            tvRightText = findView(R.id.tvRightText);

            viewTips = findView(R.id.viewTips);

            setListener();
        }
    }

    private void setListener() {
        GLViewClickUtil.setNoFastClickListener(rlLeftLayout, this);
        GLViewClickUtil.setNoFastClickListener(ivLeft, this);
        GLViewClickUtil.setNoFastClickListener(tvLeftText, this);
        GLViewClickUtil.setNoFastClickListener(llTitle, this);
        GLViewClickUtil.setNoFastClickListener(tvNavTitle, this);
        GLViewClickUtil.setNoFastClickListener(ivTitleImg, this);
        GLViewClickUtil.setNoFastClickListener(rlRightLayout, this);
        GLViewClickUtil.setNoFastClickListener(ivRight, this);
        GLViewClickUtil.setNoFastClickListener(rlRightImgAndText, this);
        GLViewClickUtil.setNoFastClickListener(ivRightImg, this);
        GLViewClickUtil.setNoFastClickListener(tvRightImgText, this);
        GLViewClickUtil.setNoFastClickListener(tvRightText, this);
        GLViewClickUtil.setNoFastClickListener(viewTips, this);
    }

    protected <T extends View> T findView(int id) {
        return (T) mActivity.findViewById(id);
    }

    public void setToolbarBgColor(int colorId) {
        if (colorId != GLConst.NONE) {
            int resColor = GLResourcesUtil.getColor(colorId);
            if (null != mToolbar) {
                mToolbar.setBackgroundColor(resColor);
            }
            if (null != llToolbarParent) {
                llToolbarParent.setBackgroundColor(resColor);
            }
        }
    }

    public void setToolbarBgColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            color = GLStringUtil.getColorText(color);
            int parseColor = Color.parseColor(color);
            if (null != mToolbar) {
                mToolbar.setBackgroundColor(parseColor);
            }
            if (null != llToolbarParent) {
                llToolbarParent.setBackgroundColor(parseColor);
            }
        }
    }

    public void setLeftIcon(int resId) {
        if (null != rlLeftLayout) {
            rlLeftLayout.setVisibility(View.VISIBLE);
        }
        if (null != ivLeft) {
            ivLeft.setVisibility(View.VISIBLE);
            ivLeft.setImageResource(resId);
        }
    }

    public void setRightIcon(int resId) {
        if (null != ivRight) {
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setImageResource(resId);
        }

        if (null != rlRightLayout) {
            rlRightLayout.setVisibility(View.VISIBLE);
        }

        int rightWidth = DEFAULT_MAX_WIDTH;
        if (null != tvNavTitle) {
            tvNavTitle.setMaxWidth(mPoint.x - rightWidth);
        }
    }

    public void setLeftCount(int count) {
        if (null != rlLeftLayout) {
            rlLeftLayout.setVisibility(View.VISIBLE);
        }
        if (null != tvLeftCount) {
            setTextCount(tvLeftCount, count);
        }
    }

    public void setRightCount(int count) {
        if (null != rlRightLayout) {
            rlRightLayout.setVisibility(View.VISIBLE);
        }
        if (null != tvRightCount) {
            setTextCount(tvRightCount, count);
        }
    }

    public void setMiddleTitle(int strId) {
        setMiddleTitle(GLResourcesUtil.getString(strId));
    }

    public void setMiddleTitle(String title) {
        if (null != tvNavTitle) {
            tvNavTitle.setVisibility(View.VISIBLE);
            tvNavTitle.setText(title);
        }
    }

    public void setMiddleTitleImg(int resId) {
        if (null != ivTitleImg) {
            ivTitleImg.setVisibility(View.VISIBLE);
            ivTitleImg.setImageResource(resId);
        }
    }

    public void setRightImgAndText(int resId, int strId) {
        setRightImgAndText(resId, GLResourcesUtil.getString(strId));
    }

    public void setRightImgAndText(int resId, String text) {
        if (null != ivRightImg) {
            ivRightImg.setImageResource(resId);
        }
        if (null != tvRightImgText) {
            tvRightImgText.setText(text);
        }

        if (null != rlRightLayout) {
            rlRightLayout.setVisibility(View.GONE);
        }
        if (null != rlRightImgAndText) {
            rlRightImgAndText.setVisibility(View.VISIBLE);
        }

        int rightWidth = DEFAULT_MAX_WIDTH + 100;
        if (null != tvNavTitle) {
            tvNavTitle.setMaxWidth(mPoint.x - rightWidth);
        }
    }

    public void setRightImg(int resId) {
        if (null != ivRightImg) {
            ivRightImg.setImageResource(resId);
        }

        if (null != rlRightLayout) {
            rlRightLayout.setVisibility(View.GONE);
        }
        if (null != rlRightImgAndText) {
            rlRightImgAndText.setVisibility(View.VISIBLE);
        }

        int rightWidth = DEFAULT_MAX_WIDTH + 100;
        if (null != tvNavTitle) {
            tvNavTitle.setMaxWidth(mPoint.x - rightWidth);
        }
    }

    public void setRightImgText(int strId) {
        setRightImgText(GLResourcesUtil.getString(strId));
    }

    public void setRightImgText(String text) {

        if (null != rlRightLayout) {
            rlRightLayout.setVisibility(View.GONE);
        }
        if (null != tvRightImgText) {
            tvRightImgText.setText(text);
        }

        if (null != rlRightImgAndText) {
            rlRightImgAndText.setVisibility(View.VISIBLE);
        }

        int rightWidth = DEFAULT_MAX_WIDTH + 100;
        if (null != tvNavTitle) {
            tvNavTitle.setMaxWidth(mPoint.x - rightWidth);
        }
    }

    public void setLeftText(int strId) {
        setLeftText(GLResourcesUtil.getString(strId));
    }

    public void setLeftText(String text) {
        if (null != tvLeftText) {
            if (TextUtils.isEmpty(text)) {
                tvLeftText.setVisibility(View.GONE);
            } else {
                tvLeftText.setVisibility(View.VISIBLE);
                tvLeftText.setText(text);
            }
        }
        int rightWidth = DEFAULT_MAX_WIDTH + 100;
        if (null != tvNavTitle) {
            tvNavTitle.setMaxWidth(mPoint.x - rightWidth);
        }
    }

    public void setLeftTextVisiblity(int visiblity) {
        if (null != tvLeftText) {
            tvLeftText.setVisibility(visiblity);
        }
    }

    public void setRightText(int strId) {
        setRightText(GLResourcesUtil.getString(strId));
    }

    public void setRightText(String text) {
        if (null != tvRightText) {
            if (TextUtils.isEmpty(text)) {
                tvRightText.setVisibility(View.GONE);
            } else {
                tvRightText.setVisibility(View.VISIBLE);
                tvRightText.setText(text);
            }
        }
        int rightWidth = DEFAULT_MAX_WIDTH;
        if (null != tvNavTitle) {
            tvNavTitle.setMaxWidth(mPoint.x - rightWidth);
        }
    }

    public void setRightIconVisiblity(int visiblity) {
        if (null != ivRight) {
            ivRight.setVisibility(visiblity);
        }

        if (null != rlRightLayout) {
            rlRightLayout.setVisibility(visiblity);
        }
    }

    public View getRightLayout() {
        return rlRightLayout;
    }

    public void clearViews() {
        if (null != rlLeftLayout) {
            rlLeftLayout.setVisibility(View.GONE);
        }
        if (null != rlRightLayout) {
            rlRightLayout.setVisibility(View.GONE);
        }
        if (null != ivLeft) {
            ivLeft.setVisibility(View.GONE);
        }
        if (null != tvLeftCount) {
            tvLeftCount.setVisibility(View.GONE);
        }
        if (null != tvLeftText) {
            tvLeftText.setVisibility(View.GONE);
        }
        if (null != tvNavTitle) {
            tvNavTitle.setText("");
            tvNavTitle.setVisibility(View.VISIBLE);
        }
        if (null != ivTitleImg) {
            ivTitleImg.setVisibility(View.GONE);
        }
        if (null != ivRight) {
            ivRight.setVisibility(View.GONE);
        }
        if (null != tvRightCount) {
            tvRightCount.setVisibility(View.GONE);
        }
        if (null != rlRightImgAndText) {
            rlRightImgAndText.setVisibility(View.GONE);
        }
        if (null != tvRightText) {
            tvRightText.setText("");
            tvRightText.setVisibility(View.GONE);
        }
    }

    private void setTextCount(TextView textView, int count) {
        if (count <= GLConst.NONE) {
            textView.setVisibility(View.GONE);
            return;
        }
        String strCount;
        if (count > GLConst.MAX_MSG_COUNT) {
            strCount = GLConst.MAX_MSG_COUNT + "+";
        } else {
            strCount = String.valueOf(count);
        }
        textView.setVisibility(View.VISIBLE);
        textView.setText(strCount);
    }

    public void setViewTipsImg(int resId) {
        if (null != viewTips) {
            if (resId == GLConst.NONE) {
                viewTips.setVisibility(View.GONE);
            } else {
                viewTips.setVisibility(View.VISIBLE);
                viewTips.setBackgroundResource(resId);
            }
        }
    }

    public void setViewTipsImgVisibility(int visibility) {
        if (null != viewTips) {
            viewTips.setVisibility(visibility);
        }
    }

    @Override
    public void onNoFastClick(View v) {
        switch (v.getId()) {
            case R.id.rlLeftLayout: // 点击左边
            case R.id.ivLeft:
                if (null != mToolbarViewClickListener) {
                    mToolbarViewClickListener.onViewClick(VIEW_LEFT_CLICK);
                }
                break;
            case R.id.llTitle: // 点击中间标题
            case R.id.tvNavTitle:
            case R.id.ivTitleImg:
                if (null != mToolbarViewClickListener) {
                    mToolbarViewClickListener.onViewClick(VIEW_CENTER_CLICK);
                }
                break;
            case R.id.rlRightLayout: // 点击右边
            case R.id.ivRight:
                if (null != mToolbarViewClickListener) {
                    mToolbarViewClickListener.onViewClick(VIEW_RIGHT_CLICK);
                }
                break;
            case R.id.rlRightImgAndText: // 点击右边图文显示
            case R.id.ivRightImg:
            case R.id.tvRightImgText:
                if (null != mToolbarViewClickListener) {
                    mToolbarViewClickListener.onViewClick(VIEW_RIGHT_IMG_AND_TEXT);
                }
                break;
            case R.id.tvRightText: // 右边的文字按钮
                if (null != mToolbarViewClickListener) {
                    mToolbarViewClickListener.onViewClick(VIEW_RIGHT_TEXT_CLICK);
                }
                break;
            case R.id.viewTips: // 点击提示
                if (null != mToolbarViewClickListener) {
                    mToolbarViewClickListener.onViewClick(VIEW_TIPS_CLICK);
                }
                break;
            case R.id.tvLeftText: // 点击左边文字
                if (null != mToolbarViewClickListener) {
                    mToolbarViewClickListener.onViewClick(VIEW_LEFT_TEXT_CLICK);
                }
                break;
        }
    }

    public interface IToolbarViewClickListener {
        void onViewClick(int clickAction);
    }
}
