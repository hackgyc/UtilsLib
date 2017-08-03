package com.gyc.kotlinutils.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.gyc.kotlinutils.ProgressSubscriber;
import com.gyc.kotlinutils.contract.CommonContract;
import com.gyc.kotlinutils.model.CommonListModel;

import java.util.Map;

/**
 * Created by Surface on 2017/03/03
 */

public class CommonListPresenter implements CommonContract.PresenterList {

    private CommonContract.View view;
    private CommonContract.ModelList model;
    private boolean isLoadding = true;

    private ProgressSubscriber.SubscriberOnErrorListener subscriberOnErrorListener;

    public void setSubscriberOnErrorListener(ProgressSubscriber.SubscriberOnErrorListener subscriberOnErrorListener) {
        this.subscriberOnErrorListener = subscriberOnErrorListener;
    }

    public CommonListPresenter(CommonContract.View view) {
        this.view = view;
        if (view instanceof Activity) {
            model = new CommonListModel((Activity) view);
        } else {
            model = new CommonListModel(((Fragment) view).getActivity());
        }
    }

    @Override
    public void excute(String uri, String method, Map<String, Object> map, SwipeRefreshLayout refreshLayout, String hint) {
        ProgressSubscriber.SubscriberOnNextListener subscriberOnNextListener = new ProgressSubscriber.SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                view.onResult(o);
            }

        };
        if (subscriberOnErrorListener != null) {
            ((CommonListModel) model).setSubscriberOnErrorListener(subscriberOnErrorListener);
        }
        model.excute(uri, method, map, isLoadding, subscriberOnNextListener, refreshLayout, hint);
    }

    /***
     * 是否显示加载框
     * @param isLoadding
     */
    public void setIsLoading(boolean isLoadding) {
        this.isLoadding = isLoadding;
    }
}