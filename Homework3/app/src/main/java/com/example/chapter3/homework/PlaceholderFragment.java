package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.chapter3.homework.recycler.MyAdapter;
import com.example.chapter3.homework.recycler.TestData;
import com.example.chapter3.homework.recycler.TestDataSet;

public class PlaceholderFragment extends Fragment {
    private LottieAnimationView animationView;
    private View target1;
    private View target2;
    private AnimatorSet animatorSet;
    // 实现聊天框显示功能部分
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        View view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        animationView= view.findViewById(R.id.wait_view);
        target1 = view.findViewById(R.id.wait_view);
        target2 = view.findViewById(R.id.recycler);

        //获取实例
        recyclerView = view.findViewById(R.id.recycler);
        //更改数据时不会变更宽高
        recyclerView.setHasFixedSize(true);
        //创建线性布局管理器
        layoutManager = new LinearLayoutManager(getActivity());
        //创建格网布局管理器
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //创建Adapter
        mAdapter = new MyAdapter(TestDataSet.getData());
        //设置Adapter每个item的点击事件
        mAdapter.setOnItemClickListener(new MyAdapter.IOnItemClickListener() {
            @Override
            public void onItemCLick(int position, TestData data) {

            }

            @Override
            public void onItemLongCLick(int position, TestData data) {

            }
        });
        //设置Adapter
        recyclerView.setAdapter(mAdapter);
//        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //动画
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(3000);
        recyclerView.setItemAnimator(animator);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入

                ObjectAnimator animator_fade = ObjectAnimator.ofFloat(target1,"alpha",1f,0);

                ObjectAnimator animator_out = ObjectAnimator.ofFloat(target2,"alpha",0,1f);
                recyclerView.setVisibility(View.VISIBLE);
                animatorSet = new AnimatorSet();
                animatorSet.playTogether(animator_fade,animator_out);
                animatorSet.start();
            }
        }, 5000);
    }
}
