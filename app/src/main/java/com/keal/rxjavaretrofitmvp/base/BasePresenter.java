package com.keal.rxjavaretrofitmvp.base;

import com.keal.rxjavaretrofitmvp.mvp.p.Presenter;
import com.keal.rxjavaretrofitmvp.mvp.v.MainView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ${Keal} on 2016/8/31.
 */
public class BasePresenter<V extends MainView> implements Presenter<V> {
    protected V view;
    private CompositeSubscription mCompositeSubscription;

    public void attachView(V view) {
        this.view = view;
    }


    @Override
    public void detachView() {
        this.view = null;
        onUnsubscribe();
    }

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    //订阅
    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }
}
