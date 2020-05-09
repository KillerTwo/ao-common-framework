package org.lwt.modal;

import java.util.HashMap;
import java.util.Map;

public class Result extends HashMap<String, Object> {

    public Result() {
        this.put("code", 0);
        this.put("msg", "success");
    }

    public Result(Integer code, String msg, Object data) {
        this.put("code", code);
        this.put("msg", msg);
        this.put("data", data);
    }

    public Result(Integer code, String msg) {
        this.put("code", code);
        this.put("msg", msg);
    }

    public static Result success(Object data) {
        return new Result(0, "success", data);
    }

    public static Result success(Integer code, Object data) {
        return new Result(code, "success", data);
    }

    public static Result error(Integer code, String msg) {
        return new Result(code, msg);
    }

    public static Result results(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);

        return r;
    }

    @Override
    public Object put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
