package com.keal.rxjavaretrofitmvp.mvp;

import android.os.Bundle;

import com.keal.rxjavaretrofitmvp.base.BaseActivity;
import com.keal.rxjavaretrofitmvp.mvp.p.MainPresenter;

/**
 * Created by ${Keal} on 2016/8/31.
 */
public abstract class MvpMainActivity<P extends MainPresenter> extends BaseActivity{
    protected P mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainPresenter =create_present();
        super.onCreate(savedInstanceState);
    }

    public abstract P create_present();
}
