package com.keal.rxjavaretrofitmvp.rxjava;

/**
 * Created by ${Keal} on 2016/8/31.
 */
public interface ApiCallback<T> {

    void onSuccess(T model);

    void onFailure(int code, String msg);

    void onCompleted();

}
