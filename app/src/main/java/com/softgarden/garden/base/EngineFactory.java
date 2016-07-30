package com.softgarden.garden.base;


import com.softgarden.garden.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

public class EngineFactory {

    public static Map<String, BaseEngine> engines = new HashMap<String, BaseEngine>();

    public static BaseEngine getEngine(Class<? extends BaseEngine> c) {
        String name = c.getSimpleName();
        if (engines.containsKey(name)) {
            LogUtils.i("获取保存的业务类");
            return engines.get(name);
        } else {
            BaseEngine engine = null;
            try {
                engine = c.newInstance();
                LogUtils.i("创建新的业务类");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            engines.put(name, engine);
            return engine;
        }

    }

}
