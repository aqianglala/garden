package com.softgarden.garden.base;


import com.softgarden.garden.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * EngineFactory
 *
 * @author 周钇冲 业务类简单工厂，将业务对象缓存起来，不每次都去new
 * @date 2015/9/8
 */
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
