package com.hsjskj.quwen.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.DebugLog;
import com.hsjskj.quwen.common.MyMvvmActivity;
import com.hsjskj.quwen.http.response.HomePublishBean;
import com.hsjskj.quwen.other.IntentKey;
import com.hsjskj.quwen.ui.activity.ImagePreviewActivity;
import com.hsjskj.quwen.ui.activity.ImageSelectActivity;
import com.hsjskj.quwen.ui.home.adapter.HomePublishAdapter;
import com.hsjskj.quwen.ui.home.viewmodel.HomePublishViewModel;
import com.hsjskj.quwen.upload.UploadBean;
import com.hsjskj.quwen.upload.UploadListener;
import com.hsjskj.quwen.upload.UploadTxImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月26日 13:08
 * description   : 首页问问发布
 */
public class HomePublishActivity extends MyMvvmActivity<HomePublishViewModel> implements BaseAdapter.OnItemClickListener, BaseAdapter.OnChildClickListener {

    private RecyclerView publishRecyclerview;
    private EditText etPublishTitle;
    private EditText etPublishContent;
    private HomePublishAdapter publishAdapter;
    private List<UploadBean> pics;
    private static final int MAX_SELECT_NUMBER = 3;
    private UploadListener listener;
    private HomePublishBean.DataBean bean = null;//编辑时 传的id  可空

    @DebugLog
    public static void start(Context context) {
        Intent intent = new Intent(context, HomePublishActivity.class);
        context.startActivity(intent);
    }

    @DebugLog
    public static void start(Context context, HomePublishBean.DataBean bean) {
        Intent intent = new Intent(context, HomePublishActivity.class);
        intent.putExtra(IntentKey.PublishBean, bean);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_publish_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        bean = getParcelable(IntentKey.PublishBean);
        publishRecyclerview = findViewById(R.id.publish_recyclerview);
        etPublishTitle = findViewById(R.id.et_publish_title);
        etPublishContent = findViewById(R.id.et_publish_content);
        publishAdapter = new HomePublishAdapter(this);
        publishAdapter.setOnItemClickListener(this);
        publishAdapter.setOnChildClickListener(R.id.iv_item_deleted, this);
        publishRecyclerview.setLayoutManager(new GridLayoutManager(this, MAX_SELECT_NUMBER));
        publishRecyclerview.setAdapter(publishAdapter);

        mViewModel.getTxCosLiveBean().observe(this, txCosBean -> {
            if (txCosBean != null) {
                listener = new UploadTxImpl(getContext(), txCosBean);
            }
        });
        loadTxCos();
    }

    private void loadTxCos() {
        mViewModel.loadTxCos(this);
    }

    @Override
    protected void onDestroy() {
        if (listener != null) {
            listener.cancel();
        }
        super.onDestroy();
    }

    @Override
    protected void initData() {
        pics = new ArrayList<>();
        pics.add(new UploadBean());
        publishAdapter.setData(pics);


        if (bean != null && bean.enclosure != null && bean.enclosure_key != null) {
            List<String> enclosure = bean.enclosure;
            for (int i = 0; i < enclosure.size(); i++) {
                if (pics.size() == MAX_SELECT_NUMBER) {
                    publishAdapter.isShowAdd = false;
                    pics.remove(0);//移除第一张占位
                }
                pics.add(new UploadBean(enclosure.get(i), bean.enclosure_key.get(i), true));
            }
            publishAdapter.notifyDataSetChanged();
        }

        if (bean != null) {
            etPublishTitle.setText("" + bean.title);
            etPublishContent.setText("" + bean.content);
        }

    }

    @Override
    public void onRightClick(View v) {
        if (listener == null) {
            loadTxCos();
            ToastUtils.show("获取上传cos失败");
            return;
        }
        String title = etPublishTitle.getText().toString();
        String content = etPublishContent.getText().toString();
        showDialog();
        mViewModel.submitPublish(this, null == bean ? null : bean.id, title, content, pics, listener).observe(this, o -> {
            hideDialog();
            if (o) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        if (position == 0) {
            if (!publishAdapter.isShowAdd) {
                toast("最多只能选取" + MAX_SELECT_NUMBER + "张");
                return;
            }
            ImageSelectActivity.start(this, data -> {
                String stringUrl = data.get(0);
                if (pics.size() == MAX_SELECT_NUMBER) {
                    publishAdapter.isShowAdd = false;
                    pics.remove(0);//移除第一张占位
                }
                pics.add(new UploadBean(stringUrl));
                publishAdapter.notifyDataSetChanged();
            });
        } else {
            ImagePreviewActivity.start(this, pics.get(position).toString());
        }
    }

    @Override
    public void onChildClick(RecyclerView recyclerView, View childView, int position) {
        if (!publishAdapter.isShowAdd) {
            publishAdapter.isShowAdd = true;
            pics.add(0, new UploadBean());
        }
        pics.remove(position);
        publishAdapter.notifyDataSetChanged();
    }
}
