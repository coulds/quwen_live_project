package com.hsjskj.quwen.ui.copy;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.hjq.base.mvvm.BaseRepository;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.common.MyMvvmActivity;
import com.hsjskj.quwen.widget.HintLayout;

import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月22日 16:33
 * description   : AndroidProject
 */
public class CopyActivity extends MyMvvmActivity<CopyViewModel> implements StatusAction {

    private TextView recordTimeTv;

    @Override
    protected int getLayoutId() {
        return R.layout.copy_mvvm_activity;
    }

    @Override
    public HintLayout getHintLayout() {
        return findViewById(R.id.hl_status_hint);
    }

    @Override
    protected void initData() {
        setOnClickListener(R.id.btn_viewmodel1, R.id.btn_viewmodel2,
                R.id.btn_record, R.id.btn_reset);
        recordTimeTv = findViewById(R.id.tv_content);
        mViewModel.getLiveData1().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                toast("按钮1返回" + aBoolean);
            }
        });

        mViewModel.getLiveData2().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                toast("按钮2返回" + s);
                recordTimeTv.setText("模拟网络请求返回: " + s);
            }
        });

        //不是每个页面都有加载，也不是每个页面只有一个状态，所有不提公共，具体在viewmodel中添加
        mViewModel.getLiveDataStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer status) {
                //状态判断设置 参考StatusActivity
                if (status == BaseRepository.STATUS_LOADING) {
                    showLoading();
                } else if (status == BaseRepository.STATUS_LOAD_END) {
                    showComplete();
                } else if (status == BaseRepository.STATUS_LOAD_ERROR) {
                    showError(v -> mViewModel.loadData1());
                }
            }
        });

        requestPermission();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_viewmodel1:
                mViewModel.loadData1();
                break;
            case R.id.btn_viewmodel2:
                mViewModel.loadData2();
                break;
            case R.id.btn_record:
                if (recording) {
                    pauseRecord();
                } else {
                    startRecord();
                }
                break;
            case R.id.btn_reset:
                resetRecord();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        pauseRecord();
        super.onPause();
    }

    //==================录音============

    private boolean recording = false;

    public void startRecord() {
        if (permission) {

        } else {
            requestPermission();
        }
    }

    public void pauseRecord() {

    }

    public void resetRecord() {

    }


    /**
     * 获取权限
     */
    private boolean permission;

    private void requestPermission() {
        XXPermissions.with(this).permission(Permission.RECORD_AUDIO).request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean all) {
                if (all) {
                    permission = true;
                }
            }
            @Override
            public void noPermission(List<String> denied, boolean quick) {
                if (quick) {
                    ToastUtils.show(R.string.common_permission_fail);
                    XXPermissions.startPermissionActivity(CopyActivity.this, false);
                } else {
                    ToastUtils.show(R.string.common_permission_hint);
                }
            }
        });
    }
}
