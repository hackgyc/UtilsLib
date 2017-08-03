package com.gyc.kotlinutils;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;

import com.google.gson.JsonObject;
import com.gyc.kotlindemo.bean.ResponseEntity;
import com.orhanobut.logger.Logger;

import rx.Subscriber;

/**
 * Created by Surface on 2017/2/10.
 */

public class ProgressSubscriber<T> extends Subscriber<T> {

    private SubscriberOnErrorListener subscriberOnErrorListener;

    public interface SubscriberOnErrorListener {

        void onError();
    }

    public void setSubscriberOnErrorListener(SubscriberOnErrorListener subscriberOnErrorListener) {
        this.subscriberOnErrorListener = subscriberOnErrorListener;
    }

    public interface SubscriberOnNextListener<T> {
        void onNext(T t);
    }

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressView pView;
    private Activity activity;

    private boolean isLodding = true;
    private SwipeRefreshLayout refreshLayout;

    public void setRefreshLayout(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    /***
     * false时不显示加载框
     *
     * @param lodding
     */
    public void setLodding(boolean lodding) {
        isLodding = lodding;
    }

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Activity activity, String hint) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.activity = activity;
        pView = new ProgressView(activity, hint);
        pView.setOnCancelListener(new ProgressView.OnCancelListener() {
            @Override
            public void onCancel(ProgressView progressView) {
                if (!isUnsubscribed()) {
                    unsubscribe();
                }
            }
        });
    }

    private void showProgressDialog() {
        if (pView != null) {
            pView.show();
        }
    }

    private void dismissProgressDialog() {
        if (pView != null) {
            pView.dismiss();
        }
    }

    @Override
    public void onStart() {
        if (isLodding) {
            showProgressDialog();
        }
    }

    @Override
    public void onCompleted() {
        if (isLodding) {
            dismissProgressDialog();
        }
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (subscriberOnErrorListener != null) {
            subscriberOnErrorListener.onError();
        }
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
        }
        if (isLodding) {
            dismissProgressDialog();
        }
        if (e.getMessage().contains("JsonReader.setLenient(true)")) {
            JsonObject json = new JsonObject();
            ResponseEntity<JsonObject> result = new ResponseEntity<JsonObject>(false, "请先登录", "3001", json);
            mSubscriberOnNextListener.onNext(result);
        } else {
            PopToast.showPopup(activity, pView, "网络请求异常,请稍候再试!");
            Logger.e("请求失败错误日志--->" + e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.onNext(t);
    }

}
