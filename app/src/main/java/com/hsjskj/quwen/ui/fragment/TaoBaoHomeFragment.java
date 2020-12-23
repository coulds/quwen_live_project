package com.hsjskj.quwen.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.ui.activity.HomeActivity;

/**
 * @author : Jun
 * time          : 2020年12月14日 14:03
 * description   : AndroidProject
 */
public class TaoBaoHomeFragment extends MyFragment<HomeActivity> {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    public static TaoBaoHomeFragment newInstance() {
        return new TaoBaoHomeFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.tao_bao_home_fragment;
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    protected void initView() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);

        for (int i = 0; i < 3; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText("title" + i);
            tab.setContentDescription("title" + i);
            tabLayout.addTab(tab);
        }
        viewPager2.setAdapter(new HomeAdapter());

        TabLayoutMediator tabLayoutMediator=  new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText("title"+position);
            }
        });
        tabLayoutMediator.attach();
    }

    @Override
    protected void initData() {

    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.VH> {

        @NonNull
        @Override
        public HomeAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.test_item, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeAdapter.VH holder, int position) {
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            holder.recyclerView.setAdapter(new HomeItemAdapter());
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        public class VH extends RecyclerView.ViewHolder {

            public RecyclerView recyclerView;

            public VH(@NonNull View itemView) {
                super(itemView);
                recyclerView = itemView.findViewById(R.id.recyclerview);
            }
        }
    }

    class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.VH> {

        @NonNull
        @Override
        public HomeItemAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.test_item2, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeItemAdapter.VH holder, int position) {
            holder.tv_1.setText("item"+position);
        }

        @Override
        public int getItemCount() {
            return 15;
        }

        public class VH extends RecyclerView.ViewHolder {

            public TextView tv_1;

            public VH(@NonNull View itemView) {
                super(itemView);
                tv_1 = itemView.findViewById(R.id.tv_1);
            }
        }
    }

}
