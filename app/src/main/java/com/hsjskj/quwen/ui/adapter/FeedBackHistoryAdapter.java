package com.hsjskj.quwen.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.ui.user.activity.MessageActivity;

import java.util.List;

/**
 * @author : sen
 * time          : 2021年01月04日 16:59
 * description   : quwen_live
 */
public class FeedBackHistoryAdapter extends RecyclerView.Adapter<FeedBackHistoryAdapter.ViewHolder> {
    private List<Object> messageActivityList;


    public FeedBackHistoryAdapter(List<Object> messageActivityList){
        this.messageActivityList = messageActivityList;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView lefMsg;
        TextView rightMsg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftLayout = (LinearLayout)itemView.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout)itemView.findViewById(R.id.right_layout);
            lefMsg = (TextView)itemView.findViewById(R.id.left_msg);
            rightMsg = (TextView)itemView.findViewById(R.id.right_msg);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.problembackhistory_message_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position%2==0){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.lefMsg.setText("1111111");
        }else {
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText("222222");
        }


    }

    @Override
    public int getItemCount() {
        return 2;
    }




}
