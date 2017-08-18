package com.gyc.kotlinutils.model;

import android.app.Activity;

import com.gyc.kotlinutils.Constant;
import com.gyc.kotlinutils.HttpMethods;
import com.gyc.kotlinutils.ProgressSubscriber;
import com.gyc.kotlinutils.contract.CommonContract;

import java.util.Map;

/**
 * Created by Heather on 2017/6/29.
 */

public class CommonObjectModel implements CommonContract.Model {
    private Activity activity;
    private ProgressSubscriber.SubscriberOnErrorListener subscriberOnErrorListener;

    public void setSubscriberOnErrorListener(ProgressSubscriber.SubscriberOnErrorListener subscriberOnErrorListener) {
        this.subscriberOnErrorListener = subscriberOnErrorListener;
    }

    public CommonObjectModel(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void excute(String uri, String method, Map<String, Object> map, boolean isLoadding, ProgressSubscriber.SubscriberOnNextListener subscriberOnNextListener, String hint) {
        ProgressSubscriber<Object> progressSubscriber = new ProgressSubscriber<>(subscriberOnNextListener, activity, hint);
        progressSubscriber.setLodding(isLoadding);
        if (subscriberOnErrorListener != null) {
            progressSubscriber.setSubscriberOnErrorListener(subscriberOnErrorListener);
        }
        HttpMethods.getInstance().getRetrofit(activity).objectRequest(progressSubscriber, Constant.INSTANCE.getBaseUrl() + uri, method, map);
    }
}
