package com.tcs.restCodeGeneration.Service;

import org.json.JSONException;

import com.tcs.restCodeGeneration.Request.Request;


public interface RestCodeGenerationService {

	public void generateController(Request request) throws JSONException;
	 public void generateService(Request request) throws JSONException;
	 public void generateServiceImpl(Request request) throws JSONException;
	 public void generatePojo(Request request) throws JSONException;
	 public void XmlToPojo(Request request);
	
	
}

