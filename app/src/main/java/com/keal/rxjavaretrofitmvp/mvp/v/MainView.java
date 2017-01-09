package com.keal.rxjavaretrofitmvp.mvp.v;

import com.keal.rxjavaretrofitmvp.mvp.m.MainModel;

/**
 * Created by ${Keal} on 2016/8/31.
 */
public interface MainView {
    void success(MainModel model);
    void error(int code, String msg);
    void showLoading();
    void hideLoading();
}
