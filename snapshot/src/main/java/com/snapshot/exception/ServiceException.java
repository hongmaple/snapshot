package com.snapshot.exception;

/**
 * 业务异常
 * 
 * @author Chan
 */
public final class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String msg;

    /**
     * 资源
     */
    private Object data;

    /**
     * 错误明细，内部调试错误
     *
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException()
    {
    }

    /**
     * 封装异常
     * @param msg 消息
     */
    public ServiceException(String msg)
    {
        this.msg = msg;
    }

    /**
     * 封装异常
     * @param msg 消息
     * @param code 错误码
     */
    public ServiceException(String msg, Integer code)
    {
        this.msg = msg;
        this.code = code;
        this.data=null;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    public String getMessage()
    {
        return msg;
    }

    public Integer getCode()
    {
        return code;
    }

    public ServiceException setMessage(String msg)
    {
        this.msg = msg;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }
}