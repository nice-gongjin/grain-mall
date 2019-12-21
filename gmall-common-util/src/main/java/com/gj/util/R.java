package com.gj.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R error() {
        return error(ResultEnum.ERROR);
    }

    public static R error(String msg) {
        R r = new R();
        r.put("code", 1);
        r.put("msg", msg);
        return r;
    }
    public static R error(ResultEnum resultEnum) {
        R r = new R();
        r.put("code", resultEnum.getCode());
        r.put("msg", resultEnum.getMsg());
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
