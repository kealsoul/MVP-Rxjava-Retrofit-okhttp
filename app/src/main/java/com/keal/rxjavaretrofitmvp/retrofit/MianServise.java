package com.keal.rxjavaretrofitmvp.retrofit;

import com.keal.rxjavaretrofitmvp.mvp.m.MainModel;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ${Keal} on 2016/8/31.
 */
public interface MianServise {
    String BASE_URL = "http://www.weather.com.cn/";

    //加载天气
    @GET("adat/sk/{cityId}.html")
    Observable<MainModel> loadData(@Path("cityId") String cityId);
}
