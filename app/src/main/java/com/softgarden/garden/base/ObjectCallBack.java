package com.softgarden.garden.base;

import com.google.gson.Gson;
import com.softgarden.garden.utils.LogUtils;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ObjectCallBack<T extends BaseDao> extends BaseCallBack {
    public ObjectCallBack(BaseActivity activity) {
        super(activity);
    }

    public ObjectCallBack(BaseActivity activity, boolean isShowLoadDialog) {
        super(activity, isShowLoadDialog);
    }

    public abstract void onSuccess(T data);

    @Override
    public void onSuccess(JSONObject result) {

        T t = null;
        try {
            if (result.length() == 0) {
                t = (T) new BaseDao();
            } else {
                Type type = getClass().getGenericSuperclass();
                Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
                Class<T> cls = (Class<T>) trueType;

                t = new Gson().fromJson(result.toString(), cls);
                LogUtils.e("data:"+result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        onSuccess(t);
    }
}
