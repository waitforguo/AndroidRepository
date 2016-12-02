package com.fausgoal.repository.modules.activites;

import com.fausgoal.repository.R;
import com.fausgoal.repository.base.GLParentActivity;
import com.fausgoal.repository.utils.GLMainJumpUtil;

/**
 * Description：主界面
 * <br/><br/>Created by Fausgoal on 2016/12/2.
 * <br/><br/>
 */
public class GLMainActivity extends GLParentActivity {

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApp.isInMain = true;
        //bridge及其他要先打开主界面的再跳转等等
        GLMainJumpUtil.toJump(mContext, getIntent());
    }

    @Override
    protected void onDestroy() {
        mApp.isInMain = false;
        super.onDestroy();
    }
}
