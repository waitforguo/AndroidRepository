package com.fausgoal.repository.modules.main;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.fausgoal.repository.R;
import com.fausgoal.repository.base.GLParentActivity;
import com.fausgoal.repository.modules.goods.GLGoodsDetailActivity;
import com.fausgoal.repository.utils.GLMainJumpUtil;
import com.fausgoal.repository.utils.GLViewClickUtil;

/**
 * Description：主界面
 * <br/><br/>Created by Fausgoal on 2016/12/2.
 * <br/><br/>
 */
public class GLMainActivity extends GLParentActivity {

    private Button btnGoodsDetail = null;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);

        btnGoodsDetail = findView(R.id.btnGoodsDetail);

        initHeader();
    }

    private void initHeader() {
        mToolbarLogic.setMiddleTitle(R.string.app_name);
    }

    @Override
    protected void setListener() {
        GLViewClickUtil.setNoFastClickListener(btnGoodsDetail, this);
    }

    @Override
    public void onNoFastClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoodsDetail:
                Intent intent = new Intent(mContext, GLGoodsDetailActivity.class);
                startActivity(intent);
                break;
        }
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
