package com.restapi.rest_api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {

	int response;

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

	
}
