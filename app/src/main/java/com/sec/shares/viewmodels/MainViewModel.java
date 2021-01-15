package com.sec.shares.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.sec.shares.app.ApiException;
import com.sec.shares.db.GpBean;
import com.sec.shares.entity.F10InfoBean;
import com.sec.shares.network.ApiCreate;
import com.sec.shares.network.BaseApi;
import com.sec.shares.network.TimeApi;
import com.sec.shares.rxjava.ResponseObserver;

import java.util.List;

public class MainViewModel extends BaseViewModel {

    private final BaseApi mBaseApi;
    private final TimeApi mTimeApi;

    private MutableLiveData<List<GpBean>> gplist;
    private MutableLiveData<F10InfoBean> f10InfoBean;

    public MainViewModel() {
        mBaseApi = ApiCreate.create(BaseApi.class);
        mTimeApi = ApiCreate.create(TimeApi.class);
    }

    public MutableLiveData<List<GpBean>> getGplist() {
        gplist = new MutableLiveData<>();
        gplist();
        return gplist;
    }

    public MutableLiveData<F10InfoBean> f10InfoBean(String id) {
        f10InfoBean = new MutableLiveData<>();
        f10Info(id);
        return f10InfoBean;
    }

    /**
     * 获取股票列表接口
     */
    private void gplist() {
        mBaseApi.baseGplist("710CF763-1E77-9F89-E426-42BFFFD7ABBD")
                .compose(applySchedulers())
                .compose(bindToCleared())
                .subscribe(new ResponseObserver<List<GpBean>>() {

                    @Override
                    public void success(List<GpBean> staffJson) {
                        gplist.postValue(staffJson);
                    }

                    @Override
                    public void error(ApiException apiException) {
                        gplist.postValue(null);
                    }
                });
    }


    /**
     * 上市公司详情接口
     */
    private void f10Info(String id) {
        mTimeApi.f10Info(id, "710CF763-1E77-9F89-E426-42BFFFD7ABBD")
                .compose(applySchedulers())
                .compose(bindToCleared())
                .subscribe(new ResponseObserver<F10InfoBean>() {

                    @Override
                    public void success(F10InfoBean f10Info) {
                        f10InfoBean.postValue(f10Info);
                    }

                    @Override
                    public void error(ApiException apiException) {
                        f10InfoBean.postValue(null);
                    }
                });
    }
}
