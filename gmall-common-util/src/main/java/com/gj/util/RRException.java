package com.gj.util;

/**
 * 自定义异常
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午10:11:27
 */
public class RRException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;

    public RRException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.message = resultEnum.getMsg();
        this.code = resultEnum.getCode();
    }
    public RRException(ResultEnum resultEnum,String extra) {
        super(resultEnum.getMsg());
        this.message = resultEnum.getMsg()+";"+extra;
        this.code = resultEnum.getCode();
    }

    public RRException(ResultEnum resultEnum, Throwable e) {
        super(resultEnum.getMsg(), e);
        this.message = resultEnum.getMsg();
        this.code = resultEnum.getCode();
    }
    public RRException(String message,Integer code){
        this.code=code;
        this.message=message;
    }

    //	@Override
    public String getMsg() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
