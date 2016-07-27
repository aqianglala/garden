package com.softgarden.garden.engine;

import com.softgarden.garden.base.BaseCallBack;
import com.softgarden.garden.base.BaseEngine;
import com.softgarden.garden.helper.HttpHelper;
import com.softgarden.garden.interfaces.UrlsAndKeys;

import org.json.JSONObject;

/**
 * Created by qiang-pc on 2016/7/25.
 */
public class LaunchEngine extends BaseEngine {

    public static void qidongye(BaseCallBack callBack){
        HttpHelper.post(UrlsAndKeys.qidongye,new JSONObject(),callBack);
    }
}
