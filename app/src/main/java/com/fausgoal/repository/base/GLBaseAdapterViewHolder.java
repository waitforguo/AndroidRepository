package com.fausgoal.repository.base;

import android.view.View;

import com.fausgoal.repository.callback.IGLOnListItemClickListener;
import com.fausgoal.repository.common.GLConst;
import com.fausgoal.repository.utils.GLViewClickUtil;


/**
 * Description：使用ListView时，的ViewHolder基类
 * <br/><br/>Created by Fausgoal on 2015/5/13.
 * <br/><br/>
 */
public abstract class GLBaseAdapterViewHolder<E> implements GLViewClickUtil.NoFastClickListener {
    protected View mView = null;
    protected int mPosition = GLConst.NONE;
    protected IGLOnListItemClickListener mClickListener = null;

    public GLBaseAdapterViewHolder(View view, IGLOnListItemClickListener listener) {
        if (null == view) {
            throw new IllegalArgumentException("view is not null~~~");
        }
        mView = view;
        mClickListener = listener;
    }

    public abstract void setValue(int position, E e);

    protected <T extends View> T findView(int id) {
        if (null == mView) {
            throw new IllegalArgumentException("view is not null!");
        }
        return (T) mView.findViewById(id);
    }

    @Override
    public void onNoFastClick(View v) {
        if (null != mClickListener) {
            mClickListener.onClickItem(mPosition, v);
        }
    }
}
