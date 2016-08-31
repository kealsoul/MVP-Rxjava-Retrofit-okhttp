package com.keal.rxjavaretrofitmvp.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.keal.rxjavaretrofitmvp.R;
import com.keal.rxjavaretrofitmvp.mvp.MvpMainActivity;
import com.keal.rxjavaretrofitmvp.mvp.m.MainModel;
import com.keal.rxjavaretrofitmvp.mvp.p.MainPresenter;
import com.keal.rxjavaretrofitmvp.mvp.v.MainView;

import butterknife.Bind;

public class MainActivity extends MvpMainActivity<MainPresenter> implements MainView{
    @Bind(R.id.text)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPresenter = create_present();
        setContentView(R.layout.activity_main);
        mainPresenter.date("101270101");
    }


    @Override
    public MainPresenter create_present() {
        return new MainPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mainPresenter != null) {
            mainPresenter.detachView();
        }
    }

    @Override
    public void success(MainModel model) {
        toast_short(model.getWeatherinfo().getCity()+model.getWeatherinfo().getIsRadar());
        textView.setText(model.getWeatherinfo().getCity()+model.getWeatherinfo().getIsRadar());
        model.getWeatherinfo().getCity();
    }

    @Override
    public void error(int code, String msg) {
        toast_short("code"+code+"              "+"msg"+msg);
    }

    @Override
    public void showLoading() {
        toast_short("showLoading");
    }

    @Override
    public void hideLoading() {
        toast_short("hideLoading");
    }
}
