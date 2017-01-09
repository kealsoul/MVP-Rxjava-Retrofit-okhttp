# Rxjava-Retrofit+okhttp
学习Rxjava-Retrofit过程中的实践，有很多不足之处，大家可以给我发私信，如果觉得有收获可以点击star<br>
***
封装Rxjava+Retrofit+okhttp，清晰的MVP架构，MVP绑定Activity（Fragment）生命周期，以避免内存泄露<br>
##包结构
* base：存放所有基础夫类<br>
* mvp：存放所有mvp类和主要界面的父类<br>
* Retrofit：存放Retrofit接口文件和配置文件<br>
* rxjava：存放rxjava的配置文件和回调文件<br>
##BasePresenter<br>
**封装每个Presenter都得初始化和销毁都在activity的生命周期里面进行。详情见：BasePresenter**
```java
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
```
##ServiceFactory
**封装ServiceFactory，反射去获取我们自定义Service中的BASE_URL字段，统一的设置回调方式，统一的设置超时控制和缓存控制，使用gson解析**
```java
public class ServiceFactory {

/*    private final Gson mGsonDateFormat;

    public ServiceFactory() {
        mGsonDateFormat = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
    }*/

    private static class SingletonHolder {
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * create a service
     *
     * @param serviceClass
     * @param <S>
     * @return
     */
    public <S> S createService(Class<S> serviceClass) {
        String baseUrl = "";
        try {
            Field field1 = serviceClass.getField("BASE_URL");
            baseUrl = (String) field1.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.getMessage();
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //.client(getOkHttpClient())
                //.addConverterFactory(GsonConverterFactory.create(mGsonDateFormat))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    private final static long DEFAULT_TIMEOUT = 10;

    private OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //设置缓存
        //File httpCacheDirectory = new File(FileUtils.getCacheDir(SolidApplication.getInstance()), "OkHttpCache");
        //httpClientBuilder.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
        return httpClientBuilder.build();
    }
}
```
##MainPresenter
**如何去使用ServiceFactory.getInstance().createService(MianServise.class).loadData(cityId)就可以得到一个Observable对象了，构建一下变的很简单了。**
```java
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
```

详细见[博客](http://www.jianshu.com/users/279e60b30fc0/timeline)！（缓慢更新中。。。。）

