package com.fausgoal.repository.modules.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fausgoal.repository.R;
import com.fausgoal.repository.base.GLBasePagerAdapter;
import com.fausgoal.repository.callback.IGLOnListItemClickListener;
import com.fausgoal.repository.common.GLConst;
import com.fausgoal.repository.utils.GLResourcesUtil;

import java.util.List;

/**
 * Description：引导页适配器
 * <br/><br/>Created by Fausgoal on 2016/12/2.
 * <br/><br/>
 */
public class GLGuideAdapter extends GLBasePagerAdapter<Integer> {

    public GLGuideAdapter(Context context, List<Integer> list, IGLOnListItemClickListener itemClickListener) {
        super(context, list, itemClickListener);
    }

    @Override
    public Object bindView(ViewGroup container, final int position) {
        View view = mInflater.inflate(R.layout.item_guide, container, false);
        final ImageView ivGuide = GLResourcesUtil.findView(view, R.id.ivGuide);
        int resId = getItem(position);
        ivGuide.setBackgroundResource(resId);

        container.addView(view, GLConst.NONE);
        if (getCount() - GLConst.ONE == position) {
            ivGuide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mClickListener) {
                        mClickListener.onClickItem(position, ivGuide);
                    }
                }
            });
        }
        return view;
    }
}
