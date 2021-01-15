package com.sec.shares.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sec.shares.R;
import com.sec.shares.adapter.GpListAdapter;
import com.sec.shares.db.GbDao;
import com.sec.shares.db.GpBean;
import com.sec.shares.db.WeatherDatabase;
import com.sec.shares.viewmodels.MainViewModel;
import com.tencent.mmkv.MMKV;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final String init = "init";

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // 设置标题
        mTvTitle.setText("全部股票");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(v -> finish());

        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText("我的关注");
        mTvRight.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MyFocusActivity.class);
            startActivity(intent);
        });

        WeatherDatabase database = WeatherDatabase.getDatabase(this);
        gbDao = database.gbDao();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCityAdapter = new GpListAdapter();

        mCityAdapter.setOnItemClickListener((view, position) -> {
            GpBean gpBean = mCityAdapter.getCityList().get(position);
            mainViewModel.f10InfoBean(gpBean.getDm()).observe(this, f10Info -> {
                Timber.i("f10Info:" + f10Info);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("data", f10Info);
                intent.putExtra("id", gpBean.getId());
                startActivity(intent);
            });
        });
        mRecyclerView.setAdapter(mCityAdapter);
        // 添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        boolean isInit = MMKV.defaultMMKV().getBoolean(init, false);
        if (isInit) {
            gbDao.queryAll().observe(this, cityList -> {
                if (cityList != null && cityList.size() > 0) {
                    mCityAdapter.setCityList(cityList);
                }
            });
        } else {
            mainViewModel.getGplist().observe(this, bglist -> {
                if (bglist != null && bglist.size() > 0) {
                    mCityAdapter.setCityList(bglist);
                }

                Timber.i("bglist:" + bglist);
                // 使用 RxJava 切换到后台线程给数据库插入数据
                Completable.fromAction(() -> gbDao.insertAll(bglist))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onComplete() {
                                Timber.i("inset gb list complete");
                                MMKV.defaultMMKV().putBoolean(init, true);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                Timber.i("inset gb list error");
                            }
                        });
            });
        }
    }

}