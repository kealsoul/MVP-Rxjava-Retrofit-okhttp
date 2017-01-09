package com.keal.rxjavaretrofitmvp.mvp.p;

import com.keal.rxjavaretrofitmvp.base.BasePresenter;
import com.keal.rxjavaretrofitmvp.mvp.m.MainModel;
import com.keal.rxjavaretrofitmvp.mvp.v.MainView;
import com.keal.rxjavaretrofitmvp.retrofit.MianServise;
import com.keal.rxjavaretrofitmvp.retrofit.ServiceFactory;
import com.keal.rxjavaretrofitmvp.rxjava.ApiCallback;
import com.keal.rxjavaretrofitmvp.rxjava.SubscriberCallBack;

/**
 * Created by ${Keal} on 2016/8/31.
 */
public class MainPresenter extends BasePresenter<MainView>{
    private MainView view;

    public MainPresenter(MainView view){
        this.view = view;
    }

    public void date(String cityId){
        view.showLoading();
        addSubscription(ServiceFactory.getInstance().createService(MianServise.class).loadData(cityId),new SubscriberCallBack(new ApiCallback<MainModel>() {
            @Override
            public void onSuccess(MainModel model) {
                view.success(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                view.error( code,  msg);
            }

            @Override
            public void onCompleted() {
                view.hideLoading();
            }
        }));
    }




}
