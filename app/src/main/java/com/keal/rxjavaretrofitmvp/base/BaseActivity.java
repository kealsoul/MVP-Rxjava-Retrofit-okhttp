package com.keal.rxjavaretrofitmvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ${Keal} on 2016/8/31.
 */
public class BaseActivity extends Activity {
    public Activity mActivity;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mActivity = this;
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
        mActivity = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        onUnsubscribe();
        super.onDestroy();
    }

    public void onUnsubscribe(){
        if(mCompositeSubscription!=null){
            mCompositeSubscription = new CompositeSubscription();
            mCompositeSubscription.unsubscribe();
        }
    }

    public void toast_long(String msg){
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT);
    }

    public void toast_short(String msg){
        Toast.makeText(mActivity,msg,Toast.LENGTH_LONG);
    }
}
