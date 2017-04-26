package com.fausgoal.repository.modules.goods;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fausgoal.repository.R;
import com.fausgoal.repository.pojo.GoodsDetailBottomPOJO;
import com.fausgoal.repository.utils.GLListUtil;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：下部分内容适配器
 * <br/><br/>Created by Fausgoal on 4/25/17.
 * <br/><br/>
 */
class GLGoodsDetailBottomAdapter extends UltimateViewAdapter<GLGoodsDetailBottomAdapter.ViewHolder> {
    private final Context mContext;
    private final List<GoodsDetailBottomPOJO> mDatas;

    GLGoodsDetailBottomAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }

    void appendData(List<GoodsDetailBottomPOJO> datas) {
        if (!GLListUtil.isEmpty(datas)) {
            mDatas.addAll(datas);
        }
    }

    public void clear() {
        mDatas.clear();
    }

    @Override
    public ViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public ViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods_detail_bottom, parent, false);
        return new ViewHolder(view);
    }

    public GoodsDetailBottomPOJO getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getAdapterItemCount() {
        return GLListUtil.getSize(mDatas);
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setValue(position, getItem(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class ViewHolder extends UltimateRecyclerviewViewHolder<GoodsDetailBottomPOJO> {

        private int mPosition;

        ViewHolder(View itemView) {
            super(itemView);
        }

        void setValue(int position, GoodsDetailBottomPOJO pojo) {
            mPosition = position;
        }
    }
}
