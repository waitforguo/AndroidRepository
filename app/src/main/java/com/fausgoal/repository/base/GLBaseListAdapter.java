/**
 *
 */
package com.fausgoal.repository.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fausgoal.repository.callback.IGLOnListItemClickListener;
import com.fausgoal.repository.common.GLConst;

import java.util.ArrayList;
import java.util.List;


/**
 * GLBaseListAdapter
 * 2014-9-1 下午5:00:16
 * author FU ZHIXUE
 *
 * @param <E> Object
 */
public abstract class GLBaseListAdapter<E> extends BaseAdapter {
    protected List<E> mList = null;
    protected Context mContext = null;
    protected LayoutInflater mInflater = null;
    protected IGLOnListItemClickListener mClickListener = null;

    public GLBaseListAdapter(Context context, List<E> list, IGLOnListItemClickListener listener) {
        super();
        this.mContext = context;
        this.mList = (null == list ? new ArrayList<E>() : list);
        this.mInflater = LayoutInflater.from(mContext);
        this.mClickListener = listener;
    }

    public void setListItemClickListener(IGLOnListItemClickListener listener) {
        mClickListener = listener;
    }

    public List<E> getList() {
        return this.mList;
    }

    public void setList(List<E> list) {
        if (null != list) {
            this.mList = list;
        }
    }

    public void append(E e) {
        if (null != e && null != this.mList) {
            this.mList.add(e);
            notifyDataSetChanged();
        }
    }

    public void append(List<E> e) {
        if (null != e && e.size() > GLConst.NONE && null != this.mList) {
            this.mList.addAll(e);
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        if (null != this.mList) {
            this.mList.remove(position);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (null != this.mList) {
            this.mList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return (null == this.mList) ? GLConst.NONE : this.mList.size();
    }

    @Override
    public E getItem(int position) {
        if (null != this.mList) {
            if (position < GLConst.NONE || position >= this.mList.size()) {
                return null;
            }
            return this.mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = bindView(position, convertView, parent);
        return convertView;
    }

    /**
     * 适配器中创建用户界面与getView用法相同
     *
     * @param position    position
     * @param convertView convertView
     * @param parent      parent
     * @return view
     */
    public abstract View bindView(int position, View convertView, ViewGroup parent);
}
