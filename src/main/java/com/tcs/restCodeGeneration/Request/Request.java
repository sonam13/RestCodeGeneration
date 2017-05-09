package com.tcs.restCodeGeneration.Request;

import java.util.ArrayList;

public class Request {

	private String serviceType;
	private String serviceMethod;
	private String serviceQueryParameters;
	private String serviceUrl;
	private String servicePath;
	private String serviceOperationName;
	private String serviceTargetNameSpace;
	private String serviceInputParameters;
	private ArrayList<String> customResponse;
	private String serviceProduces;
	private String serviceConsumes;
	private Object serviceResponse;
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceMethod() {
		return serviceMethod;
	}
	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}
	public String getServiceQueryParameters() {
		return serviceQueryParameters;
	}
	public void setServiceQueryParameters(String serviceQueryParameters) {
		this.serviceQueryParameters = serviceQueryParameters;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getServicePath() {
		return servicePath;
	}
	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}
	public String getServiceOperationName() {
		return serviceOperationName;
	}
	public void setServiceOperationName(String serviceOperationName) {
		this.serviceOperationName = serviceOperationName;
	}
	public String getServiceTargetNameSpace() {
		return serviceTargetNameSpace;
	}
	public void setServiceTargetNameSpace(String serviceTargetNameSpace) {
		this.serviceTargetNameSpace = serviceTargetNameSpace;
	}
	public String getServiceInputParameters() {
		return serviceInputParameters;
	}
	public void setServiceInputParameters(String serviceInputParameters) {
		this.serviceInputParameters = serviceInputParameters;
	}
	public ArrayList<String> getCustomResponse() {
		return customResponse;
	}
	public void setCustomResponse(ArrayList<String> customResponse) {
		this.customResponse = customResponse;
	}
	public String getServiceProduces() {
		return serviceProduces;
	}
	public void setServiceProduces(String serviceProduces) {
		this.serviceProduces = serviceProduces;
	}
	public String getServiceConsumes() {
		return serviceConsumes;
	}
	public void setServiceConsumes(String serviceConsumes) {
		this.serviceConsumes = serviceConsumes;
	}
	public Object getServiceResponse() {
		return serviceResponse;
	}
	public void setServiceResponse(Object serviceResponse) {
		this.serviceResponse = serviceResponse;
	}
	
	

}


