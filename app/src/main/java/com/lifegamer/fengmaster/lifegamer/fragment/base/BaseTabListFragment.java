package com.lifegamer.fengmaster.lifegamer.fragment.base;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.BaseViewPagerFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qianzise on 2017/10/10.
 */

public abstract class BaseTabListFragment extends Fragment{

    @BindView(R.id.tl_fragment_base_list)
    TabLayout tabLayout;
    @BindView(R.id.vp_fragment_base_list)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton actionButton;

    List<BaseFragment> fragments =new ArrayList<>();

    public BaseTabListFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_base_tab_list, container, false);
        ButterKnife.bind(this,inflate);

        viewPager.setAdapter(new BaseViewPagerFragmentAdapter(getFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onActionButtonClick();
            }
        });
        return inflate;
    }

    public void addAdapter(BaseRecyclerViewAdapter adapter){
        fragments.add(new BaseRecyclerViewFragment().setAdapter(adapter));
    }

    public abstract void onActionButtonClick();



}