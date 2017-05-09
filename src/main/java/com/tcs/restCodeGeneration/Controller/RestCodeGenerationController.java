package com.tcs.restCodeGeneration.Controller;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tcs.restCodeGeneration.Request.Request;
import com.tcs.restCodeGeneration.Service.RestCodeGenerationService;

@Controller
public class RestCodeGenerationController {
	
	@Autowired 
	RestCodeGenerationService restCodeGenerationService;
	
	@RequestMapping(value = "/restCodeGeneration", method = RequestMethod.POST)
	public @ResponseBody String getClients(@RequestBody Request request) throws JSONException {
		String msg=null;
		try
		{
			String filePath=request.getServicePath();
		
		 File f = new File(filePath);
		 try {
			FileUtils.deleteDirectory(f);
			new File(filePath).mkdirs();
			new File(filePath+ File.separator+"controller").mkdirs();
			new File(filePath+ File.separator+"pojoRequest").mkdirs();
			new File(filePath+ File.separator+"service").mkdirs();
			msg= "Code generated but pojo not generated";
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		 
		 
		 if(request.getServiceInputParameters()!=null)
		 {
			 restCodeGenerationService.generatePojo(request);
			 msg= "code generation suuccesfull";
		 }
		 
		 restCodeGenerationService.generateController(request);
		 restCodeGenerationService.generateService(request);
		 restCodeGenerationService.generateServiceImpl(request);
		 
		}
		catch(Exception e){
			e.printStackTrace();
			msg="Exception occurred while generating code";
		}
		return msg;
	}
}
