package com.fausgoal.repository.modules.goods;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fausgoal.repository.R;
import com.fausgoal.repository.base.GLParentFragment;
import com.fausgoal.repository.pojo.GoodsDetailBottomPOJO;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：商品详情下部分内容
 * <br/><br/>Created by Fausgoal on 4/25/17.
 * <br/><br/>
 */
public class GLGoodsDetailBottomFragment extends GLParentFragment {

    public static GLGoodsDetailBottomFragment newInstance() {
        return new GLGoodsDetailBottomFragment();
    }

    private View mView = null;
    private GLGoodsDetailBottomAdapter mAdapter = null;

    private boolean isLoadingFinish = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fr_goods_detail_bottom, container, false);
        return mView;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        UltimateRecyclerView urvList = findView(mView, R.id.urvList);

        urvList.setHasFixedSize(true);
        urvList.setSaveEnabled(true);
        urvList.setClipToPadding(false);

        mAdapter = new GLGoodsDetailBottomAdapter(mContext);
        urvList.setLayoutManager(new LinearLayoutManager(mContext));
        urvList.setAdapter(mAdapter);
    }

    public void loadIngData() {
        if (!isLoadingFinish) {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO: 4/25/17  获取的数据
                    List<GoodsDetailBottomPOJO> pojos = new ArrayList<>();
                    GoodsDetailBottomPOJO pojo = new GoodsDetailBottomPOJO();
                    pojos.add(pojo);

                    pojo = new GoodsDetailBottomPOJO();
                    pojos.add(pojo);

                    pojo = new GoodsDetailBottomPOJO();
                    pojos.add(pojo);

                    pojo = new GoodsDetailBottomPOJO();
                    pojos.add(pojo);

                    pojo = new GoodsDetailBottomPOJO();
                    pojos.add(pojo);

                    pojo = new GoodsDetailBottomPOJO();
                    pojos.add(pojo);

                    pojo = new GoodsDetailBottomPOJO();
                    pojos.add(pojo);

                    pojo = new GoodsDetailBottomPOJO();
                    pojos.add(pojo);

                    pojo = new GoodsDetailBottomPOJO();
                    pojos.add(pojo);

                    pojo = new GoodsDetailBottomPOJO();
                    pojos.add(pojo);

                    pojo = new GoodsDetailBottomPOJO();
                    pojos.add(pojo);

                    pojo = new GoodsDetailBottomPOJO();
                    pojos.add(pojo);

                    mAdapter.clear();
                    mAdapter.appendData(pojos);
                    mAdapter.notifyDataSetChanged();
                    isLoadingFinish = true;
                }
            }, 1000);
        }
    }
}
