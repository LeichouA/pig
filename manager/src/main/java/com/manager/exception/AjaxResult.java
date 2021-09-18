package com.manager.exception;

import lombok.Getter;

import java.util.HashMap;


/**
 * 操作消息提醒
 *
 * @author dave
 */
public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult() {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code
     *            状态码
     * @param msg
     *            返回内容
     */
    public AjaxResult(int code, String msg) {
        super.put(MsgCode.CODE_TAG.getCode(), code);
        super.put(MsgCode.MSG_TAG.getCode(), msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code
     *            状态码
     * @param msg
     *            返回内容
     * @param data
     *            数据对象
     */
    public AjaxResult(int code, String msg, Object data) {
        super.put(MsgCode.CODE_TAG.getCode(), code);
        super.put(MsgCode.MSG_TAG.getCode(), msg);
        super.put(MsgCode.DATA_TAG.getCode(), data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        return AjaxResult.success("success");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult success(Object data) {
        return AjaxResult.success("success", data);
    }


    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult error(Object data) {
        return AjaxResult.error("success", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg
     *            返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg
     *            返回内容
     * @param data
     *            数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(200, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static AjaxResult error() {
        return AjaxResult.error("error");
    }

    /**
     * 返回错误消息
     *
     * @param msg
     *            返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg
     *            返回内容
     * @param data
     *            数据对象
     * @return 警告消息
     */
    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(500, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code
     *            状态码
     * @param msg
     *            返回内容
     * @return 警告消息
     */
    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg, null);
    }

    /**
     * 操作消息提醒基础码: code.状态码 , msg.返回内容 ,data.数据对象
     */
    public enum MsgCode {

        /**
         * 状态码
         */
        CODE_TAG("code"),

        /**
         * 数据对象
         */
        DATA_TAG("data"),

        /**
         * 返回内容
         */
        MSG_TAG("msg");

        @Getter
        private String code;

        MsgCode(String code) {
            this.code = code;
        }
    }

}
