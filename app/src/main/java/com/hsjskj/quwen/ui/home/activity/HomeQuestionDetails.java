package com.hsjskj.quwen.ui.home.activity;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;

import com.hjq.base.UiUtlis;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.common.MyMvvmActivity;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.glide.GlideConfig;
import com.hsjskj.quwen.http.response.HomePublishBean;
import com.hsjskj.quwen.other.IntentKey;
import com.hsjskj.quwen.ui.home.viewmodel.HomeQuestionViewModel;
import com.hsjskj.quwen.ui.home.widget.StarTagView;
import com.hsjskj.quwen.ui.user.activity.UserPreviewActivity;
import com.hsjskj.quwen.widget.HintLayout;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月26日 10:28
 * description   : quwen_live
 */
public class HomeQuestionDetails extends MyMvvmActivity<HomeQuestionViewModel> implements StatusAction {
    private HintLayout mHintLayout;
    private ImageView mIvItemAvatar;
    private AppCompatTextView mIvItemName;
    private StarTagView mStarTag;
    private AppCompatTextView mTvItemTime;
    private AppCompatTextView mTvItemTitle;
    private AppCompatTextView mTvItemContent;
    private NineGridView mNineGridView;

    @Override
    protected int getLayoutId() {
        return R.layout.home_question_details_activity;
    }

    @Override
    protected void initData() {
        mViewModel.getLiveDataDetails().observe(this, o -> {
            if (o == null) {
                showError(v -> mViewModel.loadDataDetails(this, getString(IntentKey.ID)));
            } else {
                showComplete();
                setViewNinePic(o);
            }
        });
        showLoading();
        mViewModel.loadDataDetails(this, getString(IntentKey.ID));
    }

    private void setViewNinePic(HomePublishBean.DataBean item) {
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<String> enclosure = item.enclosure;
        if (enclosure != null) {
            for (String s : enclosure) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(s);
                info.setBigImageUrl(s);
                imageInfo.add(info);
            }
        }
        mNineGridView.setAdapter(new NineGridViewClickAdapter(getContext(), imageInfo));

        mIvItemName.setText(item.getShowName());
        mTvItemTime.setText("" + item.create_time);
        mTvItemContent.setText("" + item.content);
        mTvItemTitle.setText("" + item.title);
        mStarTag.setTagText(item.constellation, item.isMale(),item.isSetMale());
        GlideApp.with(getContext()).load(item.avatar).apply(GlideConfig.requestOptionsAvatar).into(mIvItemAvatar);
        mIvItemAvatar.setOnClickListener(v -> {
            UserPreviewActivity.start(getContext(),item.user_id);
        });
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    protected void initView() {
        super.initView();
        mHintLayout = (HintLayout) findViewById(R.id.hint_layout);
        mIvItemAvatar =findViewById(R.id.iv_item_avatar);
        mIvItemName = (AppCompatTextView) findViewById(R.id.iv_item_name);
        mStarTag = (StarTagView) findViewById(R.id.star_tag);
        mTvItemTime = (AppCompatTextView) findViewById(R.id.tv_item_time);
        mTvItemTitle = (AppCompatTextView) findViewById(R.id.tv_item_title);
        mTvItemContent = (AppCompatTextView) findViewById(R.id.tv_item_content);
        mNineGridView = (NineGridView) findViewById(R.id.nineGridView);


        mNineGridView.setGridSpacing(10);
        mNineGridView.setMaxSize(3);
        //详情不显示一张显示多少
//        mNineGridView.setSingleImageSize(UiUtlis.dp2px(getContext(),110));
    }
}
