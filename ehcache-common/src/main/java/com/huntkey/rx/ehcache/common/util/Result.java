package com.huntkey.rx.ehcache.common.util;

public class Result {
	public static Integer RECODE_SUCCESS = Integer.valueOf(1);
	public static Integer RECODE_ERROR = Integer.valueOf(0);
	public static Integer RECODE_UNLOGIN = Integer.valueOf(-1);
	public static Integer RECODE_VALIDATE_ERROR = Integer.valueOf(-2);
	private String errMsg;
	private Integer retCode;
	private Object data;

	public Result() {
		this.retCode = RECODE_SUCCESS;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Integer getRetCode() {
		return this.retCode;
	}

	public void setRetCode(Integer retCode) {
		this.retCode = retCode;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result{" +
				"errMsg='" + errMsg + '\'' +
				", retCode=" + retCode +
				", data=" + data +
				'}';
	}
}
