package com.sec.shares.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sec.shares.R;
import com.sec.shares.adapter.GpListAdapter;
import com.sec.shares.db.GbDao;
import com.sec.shares.db.WeatherDatabase;
import com.sec.shares.viewmodels.MainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MyFocusActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    private GbDao gbDao;

    @BindView(R.id.mTvTitle)
    TextView mTvTitle;
    @BindView(R.id.mIvBack)
    ImageView mIvBack;
    @BindView(R.id.mTvRight)
    TextView mTvRight;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private GpListAdapter mCityAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // 设置标题
        mTvTitle.setText("我的关注");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(v -> finish());

        WeatherDatabase database = WeatherDatabase.getDatabase(this);
        gbDao = database.gbDao();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCityAdapter = new GpListAdapter();

        mCityAdapter.setOnItemClickListener((view, position) -> {
            String dm = mCityAdapter.getCityList().get(position).getDm();
            mainViewModel.f10InfoBean(dm).observe(this, f10Info -> {
                Timber.i("f10Info:" + f10Info);
                Intent intent = new Intent(MyFocusActivity.this, DetailActivity.class);
                intent.putExtra("data", f10Info);
                startActivity(intent);
            });
        });
        mRecyclerView.setAdapter(mCityAdapter);
        // 添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        gbDao.queryFocus().observe(this, cityList -> {
            if (cityList != null && cityList.size() > 0) {
                mCityAdapter.setCityList(cityList);
            }
        });
    }
}
