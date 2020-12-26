package com.hsjskj.quwen.ui.home.activity;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;

import com.hjq.base.UiUtlis;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.common.MyMvvmActivity;
import com.hsjskj.quwen.ui.home.viewmodel.HomeQuestionViewModel;
import com.hsjskj.quwen.ui.home.widget.StarTagView;
import com.hsjskj.quwen.widget.HintLayout;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * @author : Jun
 * time          : 2020年12月26日 10:28
 * description   : quwen_live
 */
public class HomeQuestionDetails extends MyMvvmActivity<HomeQuestionViewModel> implements StatusAction {
    private HintLayout mHintLayout;
    private RoundedImageView mIvItemAvatar;
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
            showComplete();

            setViewNinePic(o);
        });
        showLoading();
        mViewModel.loadDataDetails();
    }

    private void setViewNinePic(Object o) {
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2879484891,1434073852&fm=15&gp=0.jpg");
            info.setBigImageUrl("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2879484891,1434073852&fm=15&gp=0.jpg");
            imageInfo.add(info);
        }
        mNineGridView.setAdapter(new NineGridViewClickAdapter(getContext(), imageInfo));
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    protected void initView() {
        super.initView();
        mHintLayout = (HintLayout) findViewById(R.id.hint_layout);
        mIvItemAvatar = (RoundedImageView) findViewById(R.id.iv_item_avatar);
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
