package org.lwt.modal;

public class Result {

    private Integer code;

    private String msg;

    private Object data;

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result(200, "success", data);
    }



    public static Result success(Integer code, Object data) {
        return new Result(code, "success", data);
    }

    public static Result error(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
