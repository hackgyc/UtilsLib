package com.gyc.kotlinutils.contract;

import android.support.v4.widget.SwipeRefreshLayout;

import com.gyc.kotlinutils.ProgressSubscriber;

import java.util.Map;

/**
 * Created by Heather on 2017/6/29.
 */

public interface CommonContract {
    public interface View {
        void onResult(Object o);
    }

    public interface Presenter {
        void excute(String uri, String method, Map<String, Object> map, String hint);
    }

    public interface Model {
        void excute(String uri, String method, Map<String, Object> map, boolean isLoadding, ProgressSubscriber.SubscriberOnNextListener subscriberOnNextListener, String hint);
    }

    public interface PresenterList {
        void excute(String uri, String method, Map<String, Object> map, SwipeRefreshLayout refreshLayout, String hint);
    }

    public interface ModelList {
        void excute(String uri, String method, Map<String, Object> map, boolean isLoadding, ProgressSubscriber.SubscriberOnNextListener subscriberOnNextListener, SwipeRefreshLayout refreshLayout, String hint);
    }
}
