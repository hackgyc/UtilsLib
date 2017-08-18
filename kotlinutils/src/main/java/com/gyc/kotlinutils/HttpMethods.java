package com.gyc.kotlinutils;

import android.content.Context;

import com.google.gson.JsonObject;
import com.gyc.kotlindemo.bean.ResponseEntity;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Surface on 2017/2/10.
 */

public class HttpMethods {

    private static final int DEFAULT_TIMEOUT = 10;

    private Retrofit retrofit;

    public HttpMethods getRetrofit(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieManger(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.INSTANCE.getBaseUrl())
                .build();

        return this;
    }

    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /***
     * 通用对象GET请求接口 path：请求字串   map：参数
     */
    private interface CommonObjGetRequest {
        @GET
        Observable<ResponseEntity<JsonObject>> execute(@Url String url, @QueryMap Map<String, Object> map);
    }

    /***
     * 通用对象POST请求接口 path：请求字串   map：参数
     */
    private interface CommonObjPostRequest {
        @POST
        Observable<ResponseEntity<JsonObject>> execute(@Url String url, @QueryMap Map<String, Object> map);
    }

    /***
     * 通用列表GET请求接口 path：请求字串   map：参数
     */
    private interface CommonListGetRequest {
        @GET
        Observable<ResponseEntity<List<JsonObject>>> execute(@Url String url, @QueryMap Map<String, Object> map);
    }

    /***
     * 通用列表POST请求接口 path：请求字串   map：参数
     */
    private interface CommonListPostRequest {
        @POST
        Observable<ResponseEntity<List<JsonObject>>> execute(@Url String url, @QueryMap Map<String, Object> map);
    }

    /***
     * 通用JsonObject对象GET请求接口 path：请求字串   map：参数
     */
    public interface CommonJsonObjectGetRequest {
        @GET
        Observable<JsonObject> execute(@Url String url, @QueryMap Map<String, Object> map);
    }

    /***
     * 通用JsonObject对象GET请求接口 path：请求字串   map：参数
     */
    public interface CommonJsonObjectPostRequest {
        @POST
        Observable<JsonObject> execute(@Url String url, @QueryMap Map<String, Object> map);
    }

    /***
     * 通用JsonObject对象GET请求接口 path：请求字串   map：参数
     */
    public interface CommonListJsonObjectGetRequest {
        @GET
        Observable<List<JsonObject>> execute(@Url String url, @QueryMap Map<String, Object> map);
    }

    /***
     * 通用JsonObject对象GET请求接口 path：请求字串   map：参数
     */
    public interface CommonListJsonObjectPostRequest {
        @POST
        Observable<List<JsonObject>> execute(@Url String url, @QueryMap Map<String, Object> map);
    }

    /***
     * 通用对象GET请求接口 path：请求字串   map：参数
     */
    public interface CommonObjectGetRequest {
        @GET
        Observable<Object> execute(@Url String url, @QueryMap Map<String, Object> map);
    }

    /***
     * 通用对象POST请求接口 path：请求字串   map：参数
     */
    public interface CommonObjectPostRequest {
        @POST
        Observable<Object> execute(@Url String url, @QueryMap Map<String, Object> map);
    }

    //添加线程管理并订阅
    private void toSubscribe(Observable o, Subscriber s) {
        o.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(s);
    }

    public void objRequest(Subscriber<ResponseEntity<JsonObject>> subscriber, String url, String method, Map<String, Object> map) {
        Logger.e("请求url--->" + url + "\n请求方式--->" + method + "\n参数--->" + map.toString());
        Observable<ResponseEntity<JsonObject>> observable;
        if (method.equals("get")) {
            observable = retrofit.create(CommonObjGetRequest.class).execute(url, map);
        } else {
            observable = retrofit.create(CommonObjPostRequest.class).execute(url, map);
        }
        toSubscribe(observable, subscriber);
    }

    public void listRequest(Subscriber<ResponseEntity<List<JsonObject>>> subscriber, String url, String method, Map<String, Object> map) {
        Logger.e("请求url--->" + url + "\n请求方式--->" + method + "\n参数--->" + map.toString());
        Observable<ResponseEntity<List<JsonObject>>> observable;
        if (method.equals("get")) {
            observable = retrofit.create(CommonListGetRequest.class).execute(url, map);
        } else {
            observable = retrofit.create(CommonListPostRequest.class).execute(url, map);
        }
        toSubscribe(observable, subscriber);
    }

    public void objectRequest(Subscriber<Object> subscriber, String url, String method, Map<String, Object> map) {
        Logger.e("请求url--->" + url + "\n请求方式--->" + method + "\n参数--->" + map.toString());
        Observable<Object> observable;
        if (method.equals("get")) {
            observable = retrofit.create(CommonObjectGetRequest.class).execute(url, map);
        } else {
            observable = retrofit.create(CommonObjectPostRequest.class).execute(url, map);
        }
        toSubscribe(observable, subscriber);
    }

    public void jsonObjectRequest(Subscriber<JsonObject> subscriber, String url, String method, Map<String, Object> map) {
        Logger.e("请求url--->" + url + "\n请求方式--->" + method + "\n参数--->" + map.toString());
        Observable<JsonObject> observable;
        if (method.equals("get")) {
            observable = retrofit.create(CommonJsonObjectGetRequest.class).execute(url, map);
        } else {
            observable = retrofit.create(CommonJsonObjectPostRequest.class).execute(url, map);
        }
        toSubscribe(observable, subscriber);
    }


    public void listJsonObjectRequest(Subscriber<List<JsonObject>> subscriber, String url, String method, Map<String, Object> map) {
        Logger.e("请求url--->" + url + "\n请求方式--->" + method + "\n参数--->" + map.toString());
        Observable<List<JsonObject>> observable;
        if (method.equals("get")) {
            observable = retrofit.create(CommonListJsonObjectGetRequest.class).execute(url, map);
        } else {
            observable = retrofit.create(CommonListJsonObjectPostRequest.class).execute(url, map);
        }
        toSubscribe(observable, subscriber);
    }
}
