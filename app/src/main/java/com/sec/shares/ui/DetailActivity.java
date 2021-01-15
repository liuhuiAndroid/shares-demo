package com.sec.shares.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.sec.shares.R;
import com.sec.shares.db.GbDao;
import com.sec.shares.db.WeatherDatabase;
import com.sec.shares.entity.F10InfoBean;
import com.sec.shares.viewmodels.MainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    private GbDao gbDao;

    @BindView(R.id.mTvTitle)
    TextView mTvTitle;
    @BindView(R.id.mIvBack)
    ImageView mIvBack;
    @BindView(R.id.mContainer)
    LinearLayout mContainer;
    @BindView(R.id.mTvRight)
    TextView mTvRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // 设置标题
        mTvTitle.setText("上市公司基本面信息");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(v -> finish());

        WeatherDatabase database = WeatherDatabase.getDatabase(this);
        gbDao = database.gbDao();

        int id = getIntent().getIntExtra("id", -1);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText("加关注");
        mTvRight.setOnClickListener(view -> {

            // 使用 RxJava 切换到后台线程给数据库插入数据
            Completable.fromAction(() -> {
                gbDao.collect(id);
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onComplete() {
                            Toast.makeText(DetailActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Timber.i("inset city list error");
                        }
                    });


        });

        F10InfoBean f10InfoBean = (F10InfoBean) getIntent().getSerializableExtra("data");
        if (f10InfoBean != null) {
            addView("公司名称:" + f10InfoBean.getName());
            addView("公司英文名称:" + f10InfoBean.getEname());
            addView("上市市场:" + f10InfoBean.getMarket());
            addView("概念及板块:" + f10InfoBean.getIdea());
            addView("发行价格（元）:" + f10InfoBean.getSprice());
            addView("主承销商:" + f10InfoBean.getPrincipal());
            addView("成立日期:" + f10InfoBean.getRdate());
            addView("注册资本:" + f10InfoBean.getRprice());
            addView("机构类型:" + f10InfoBean.getInstype());
            addView("组织形式:" + f10InfoBean.getOrgan());
            addView("董事会秘书:" + f10InfoBean.getSecre());
            addView("公司电话:" + f10InfoBean.getPhone());
            addView("董秘电话:" + f10InfoBean.getSphone());
            addView("公司传真:" + f10InfoBean.getFax());
            addView("董秘传真:" + f10InfoBean.getSfax());
            addView("公司电子邮箱:" + f10InfoBean.getEmail());
            addView("董秘电子邮箱:" + f10InfoBean.getSemail());
            addView("公司网站:" + f10InfoBean.getSite());
            addView("证券简称更名历史:" + f10InfoBean.getOname());
            addView("注册地址:" + f10InfoBean.getAddr());
            addView("发行市盈率:" + f10InfoBean.getPe());
            addView("首发前总股本（万股）:" + f10InfoBean.getFirgu());
            addView("首发后总股本（万股）:" + f10InfoBean.getLastgu());
            addView("发行费用总额（万元）:" + f10InfoBean.getPubfee());
            addView("实际发行量（万股）:" + f10InfoBean.getRealgu());
            addView("预计募集资金（万元）:" + f10InfoBean.getPlanm());
            addView("实际募集资金合计（万元）:" + f10InfoBean.getRealm());
            addView("募集资金净额（万元）:" + f10InfoBean.getCollect());
            addView("承销费用（万元）:" + f10InfoBean.getSignfee());
            addView("招股公告日:" + f10InfoBean.getPdate());
        }

    }

    public void addView(String name) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_city, null, false);
        TextView mTvDetail = view.findViewById(R.id.mTvDetail);
        mTvDetail.setText(name);
        mContainer.addView(view);
    }
}
