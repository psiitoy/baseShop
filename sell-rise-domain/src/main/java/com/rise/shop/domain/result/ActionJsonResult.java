package com.rise.shop.domain.result;

/**
 * Created by wangdi on 15-2-27.
 */
public class ActionJsonResult {

    private boolean success;
    private String msg;
    private Object obj;

    @Override
    public String toString() {
        return "ActionJsonResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
