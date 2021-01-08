package com.hsjskj.quwen.ui.my.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.base.UiUtlis;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.http.response.HomePublishBean;
import com.hsjskj.quwen.ui.home.widget.StarTagView;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Administrator :ZB
 * 2021/1/6 0006
 * describe :
 **/
public class ReleaseAdapter extends MyAdapter<HomePublishBean.DataBean> {
    private int TYPE_LEFT = 1;
    private int TYPE_RIGHT = 2;
    private int TYPE;

    public ReleaseAdapter(@NonNull Context context, int TYPE) {
        super(context);
        this.TYPE = TYPE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LEFT) {
            return new ViewHolderLeft();
        } else {
            return new ViewHolderRight();
        }

    }

    private final class ViewHolderLeft extends MyAdapter.ViewHolder {
        TextView title, time, status, tv_edit, delete;
        private NineGridView nineGridView;

        private ViewHolderLeft() {
            super(R.layout.item_release_left);
            nineGridView = (NineGridView) findViewById(R.id.nineGridView);
            nineGridView.setGridSpacing(10);
            nineGridView.setMaxSize(3);
            nineGridView.setSingleImageSize(UiUtlis.dp2px(getContext(), 110));
            title = (TextView) findViewById(R.id.title);
            time = (TextView) findViewById(R.id.time);
            status = (TextView) findViewById(R.id.status);
            tv_edit = (TextView) findViewById(R.id.tv_edit);
            delete = (TextView) findViewById(R.id.delete);
        }

        @Override
        public void onBindView(int position) {

            HomePublishBean.DataBean item = getItem(position);
            title.setText(item.getTitle() + ":" + item.getContent());
            time.setText(item.getCreate_time());
            status.setText(item.getStatus());
            if (item.getStatus().equals("待审核")) {
                tv_edit.setVisibility(View.GONE);
            }
            if (item.getStatus().equals("已打回")) {

            }
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

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemViewListener.delete(item.getId(),position);
                }
            });
            nineGridView.setAdapter(new NineGridViewClickAdapter(getContext(), imageInfo));
        }
    }

    private final class ViewHolderRight extends MyAdapter.ViewHolder {


        private ViewHolderRight() {
            super(R.layout.item_release_left);


        }

        @Override
        public void onBindView(int position) {

            // nineGridView.setAdapter(new NineGridViewClickAdapter(getContext(), imageInfo));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (TYPE == 1) {
            return TYPE_LEFT;
        }
        if (TYPE == 1) {
            return TYPE_RIGHT;
        }
        return super.getItemViewType(position);
    }

    public interface ItemViewListener {
        void delete(String id, int pos);
    }

    private ItemViewListener itemViewListener;

    public void setItemViewListener(ItemViewListener itemViewListener) {
        this.itemViewListener = itemViewListener;
    }
}
