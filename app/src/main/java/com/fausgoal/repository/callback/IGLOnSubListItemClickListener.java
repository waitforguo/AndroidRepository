package com.fausgoal.repository.callback;

import android.view.View;

/**
 * Description：点击list item的回调
 * <br/><br/>Created by Fausgoal on 2015/5/12.
 * <br/><br/>
 */
public interface IGLOnSubListItemClickListener extends IGLOnListItemClickListener {
    void onClickItem(int position, int subPosition, View v);
}
