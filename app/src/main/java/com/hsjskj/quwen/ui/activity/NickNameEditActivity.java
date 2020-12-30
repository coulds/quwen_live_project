package com.hsjskj.quwen.ui.activity;

import android.view.View;
import com.hjq.widget.view.ClearEditText;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;

public class NickNameEditActivity extends MyActivity {
    ClearEditText clearEditText;

    /* access modifiers changed from: protected */
    @Override // com.hjq.base.BaseActivity
    public int getLayoutId() {
        return R.layout.activity_nickname_edit;
    }

    /* access modifiers changed from: protected */
    @Override // com.hjq.base.BaseActivity
    public void initView() {
        this.clearEditText = (ClearEditText) findViewById(R.id.et_publish_title);
    }

    /* access modifiers changed from: protected */
    @Override // com.hjq.base.BaseActivity
    public void initData() {
    }

    @Override // com.hsjskj.quwen.action.TitleBarAction, com.hjq.bar.OnTitleBarListener
    public void onRightClick(View v) {
        this.clearEditText.getEditableText().toString();
        toast("保存成功");
    }
}
