package com.gyc.kotlinutils.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.gyc.kotlinutils.ProgressSubscriber;
import com.gyc.kotlinutils.contract.CommonContract;
import com.gyc.kotlinutils.model.CommonJsonObjectModel;

import java.util.Map;

/**
 * Created by Heather on 2017/6/29.
 */

public class CommonJsonObjectPresenter implements CommonContract.Presenter {
    private CommonContract.View view;
    private CommonContract.Model model;
    private boolean isLoadding = true;

    private ProgressSubscriber.SubscriberOnErrorListener subscriberOnErrorListener;

    public void setSubscriberOnErrorListener(ProgressSubscriber.SubscriberOnErrorListener subscriberOnErrorListener) {
        this.subscriberOnErrorListener = subscriberOnErrorListener;
    }

    public CommonJsonObjectPresenter(CommonContract.View view) {
        this.view = view;
        if (view instanceof Activity) {
            model = new CommonJsonObjectModel((Activity) view);
        } else {
            model = new CommonJsonObjectModel(((Fragment) view).getActivity());
        }
    }

    @Override
    public void excute(String uri, String method, Map<String, Object> map, String hint) {
        ProgressSubscriber.SubscriberOnNextListener subscriberOnNextListener = new ProgressSubscriber.SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                view.onResult(o);
            }

        };
        if (subscriberOnErrorListener != null) {
            ((CommonJsonObjectModel) model).setSubscriberOnErrorListener(subscriberOnErrorListener);
        }
        model.excute(uri, method, map, isLoadding, subscriberOnNextListener, hint);
    }

    /***
     * 是否显示加载框
     * @param isLoadding
     */
    public void setIsLoading(boolean isLoadding) {
        this.isLoadding = isLoadding;
    }
}
