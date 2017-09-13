package com.wanfin.fpd.modules.contract;

import javax.ws.rs.core.Response;

public class AutoVo {
	private boolean isAuto;
	private Response response;
	public boolean isAuto() {
		return isAuto;
	}
	public void setAuto(boolean isAuto) {
		this.isAuto = isAuto;
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
}