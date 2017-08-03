package com.gyc.kotlinutils;

/**
 * Created by Surface on 2016/11/8.
 */

public class ObjEvent {

    private String flag;
    private Object object;

    public ObjEvent(String flag, Object object) {
        this.flag = flag;
        this.object = object;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
