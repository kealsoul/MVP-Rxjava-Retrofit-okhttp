# Rxjava-Retrofit-MVP
封装Rxjava+Retrofit，清晰的MVP架构
MVP绑定Activity（Fragment）生命周期，以避免内存泄露
包结构
base：存放所有基础夫类
mvp：存放所有mvp类和主要界面的父类
Retrofit：存放Retrofit接口文件和配置文件
rxjava：存放rxjava的配置文件和回调文件

封装每个Presenter都得初始化和销毁都在activity的生命周期里面进行。详情见：BasePresenter
封装ServiceFactory，反射去获取我们自定义Service中的BASE_URL字段，统一的设置回调方式，统一的设置超时控制和缓存控制。详情见：ServiceFactory

详细见博客http://www.jianshu.com/users/279e60b30fc0/timeline（缓慢更新中。。。。）

