package com.gyc.kotlinutils.model;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;

import com.gyc.kotlinutils.Constant;
import com.gyc.kotlinutils.HttpMethods;
import com.gyc.kotlinutils.ProgressSubscriber;
import com.gyc.kotlinutils.contract.CommonContract;
import com.google.gson.JsonObject;
import com.gyc.kotlindemo.bean.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Surface on 2017/03/03
 */

public class CommonListModel implements CommonContract.ModelList {

    private Activity activity;
    private ProgressSubscriber.SubscriberOnErrorListener subscriberOnErrorListener;

    public void setSubscriberOnErrorListener(ProgressSubscriber.SubscriberOnErrorListener subscriberOnErrorListener) {
        this.subscriberOnErrorListener = subscriberOnErrorListener;
    }

    public CommonListModel(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void excute(String uri, String method, Map<String, Object> map, boolean isLoadding, ProgressSubscriber.SubscriberOnNextListener subscriberOnNextListener, SwipeRefreshLayout refreshLayout, String hint) {
        ProgressSubscriber<ResponseEntity<List<JsonObject>>> progressSubscriber = new ProgressSubscriber<>(subscriberOnNextListener, activity, hint);
        if (refreshLayout != null) {
            progressSubscriber.setRefreshLayout(refreshLayout);
        }
        if (subscriberOnErrorListener != null) {
            progressSubscriber.setSubscriberOnErrorListener(subscriberOnErrorListener);
        }
        HttpMethods.getInstance().getRetrofit(activity).listRequest(progressSubscriber, Constant.INSTANCE.getBaseUrl() + uri, method, map);
    }
}