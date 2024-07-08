package com.fu.springbootweb;

public class Response {
    private int code;
    private String msg;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Response() {}

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    static public Response success(String data) {
        return new Response(200, "success", data);
    }
}
