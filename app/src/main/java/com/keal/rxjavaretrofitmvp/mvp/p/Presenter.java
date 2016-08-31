package com.keal.rxjavaretrofitmvp.mvp.p;

/**
 * Created by ${Keal} on 2016/8/31.
 */
public interface Presenter<V> {
    void attachView(V view);

    void detachView();
}
