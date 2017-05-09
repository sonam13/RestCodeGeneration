package com.tcs.restCodeGeneration.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tcs.restCodeGeneration.Request.Request;

@Service
public class RestCodeGenerationServiceImpl implements RestCodeGenerationService
{
	public void generateController(Request request) throws JSONException{
		JSONObject serviceInputParameters = null;
		 StringBuilder classFormat = new StringBuilder();
		 System.out.println(request.getServiceMethod());
		 classFormat.append("package;\n\n");
		 classFormat.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		 classFormat.append("import org.springframework.http.MediaType;\n");
		 classFormat.append("import org.springframework.web.bind.annotation.RequestBody;\n");
		 classFormat.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
		 classFormat.append("import org.springframework.web.bind.annotation.RequestMethod;\n");
		 classFormat.append("import org.springframework.web.bind.annotation.ResponseBody;\n");
		 classFormat.append("public class "+ request.getServiceOperationName()+"Controller {\n\n");
		 classFormat.append("@Autowired\n");
		 classFormat.append(request.getServiceOperationName()+"Service "+request.getServiceOperationName().toLowerCase()+"Service;\n\n");
		 classFormat.append("@RequestMapping(method = RequestMethod."+request.getServiceMethod().toUpperCase()+", value =\"/"+request.getServiceOperationName()+"\")\n");
		 classFormat.append("public @ResponseBody");
		 if(request.getServiceInputParameters()!=null && !request.getServiceType().equals("QueryParam")){
				classFormat.append(" Object "+ request.getServiceOperationName()+"Details ( "+request.getServiceOperationName()+"Request request){\n");
			}
			else if(request.getServiceInputParameters()!=null && request.getServiceType().equals("QueryParam")){
				 serviceInputParameters = new JSONObject(request.getServiceInputParameters());
				classFormat.append("public Object "+ request.getServiceOperationName()+"Details (");
				for(int i=serviceInputParameters.names().length();i>0;i--){
					if(i-1==0)
						classFormat.append(serviceInputParameters.get(serviceInputParameters.names().getString(i-1)).getClass().toString().replaceAll("class", "")+" "+serviceInputParameters.names().getString(i-1));
					else
						classFormat.append(serviceInputParameters.get(serviceInputParameters.names().getString(i-1)).getClass().toString().replaceAll("class", "")+" "+serviceInputParameters.names().getString(i-1)+",");
				}
				classFormat.append("){\n");
			}
			else
				classFormat.append("public Object "+ request.getServiceOperationName()+"Details (){\n");
		 classFormat.append("Object response = "+request.getServiceOperationName().toLowerCase()+"Service.get"+request.getServiceOperationName()+"Details(");
				 if(request.getServiceInputParameters()!=null && !request.getServiceType().equals("QueryParam"))
					 classFormat.append("request);\n");
				else if(request.getServiceInputParameters()!=null && request.getServiceType().equals("QueryParam")){
						 serviceInputParameters = new JSONObject(request.getServiceInputParameters());
						for(int i=serviceInputParameters.names().length();i>0;i--){
							if(i-1==0)
								classFormat.append(serviceInputParameters.names().getString(i-1));
							else
								classFormat.append(serviceInputParameters.names().getString(i-1)+",");
						}
						classFormat.append(");\n");
					}
				else
					classFormat.append(");\n");
		 classFormat.append("return response;\n");
		 classFormat.append("}\n}\n");
		 
		 FileOutputStream fop = null;
			File file;
			file = new File(request.getServicePath()+"\\controller\\"+request.getServiceOperationName()+"Controller.java");
			String content = classFormat.toString();
			try {
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}
				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
				fop.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (fop != null) {
						fop.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	
	}
	
	 public void generateService(Request request) throws JSONException
	 {
		 JSONObject serviceInputParameters = null;
		 FileOutputStream fop = null;
		 File file;
	 StringBuilder classFormat = new StringBuilder(); 
	 classFormat.append("package;\n\n");
	 classFormat.append("public interface "+request.getServiceOperationName()+"Service {\n\n");
	 if(request.getServiceInputParameters()!=null && !request.getServiceType().equals("QueryParam")){
			classFormat.append(" Object "+ request.getServiceOperationName()+"Details ( "+request.getServiceOperationName()+"Request request);\n");
		}
		else if(request.getServiceInputParameters()!=null && request.getServiceType().equals("QueryParam")){
			 serviceInputParameters = new JSONObject(request.getServiceInputParameters());
			classFormat.append("public Object "+ request.getServiceOperationName()+"Details (");
			for(int i=serviceInputParameters.names().length();i>0;i--){
				if(i-1==0)
					classFormat.append(serviceInputParameters.get(serviceInputParameters.names().getString(i-1)).getClass().toString().replaceAll("class", "")+" "+serviceInputParameters.names().getString(i-1));
				else
					classFormat.append(serviceInputParameters.get(serviceInputParameters.names().getString(i-1)).getClass().toString().replaceAll("class", "")+" "+serviceInputParameters.names().getString(i-1)+",");
			}
			classFormat.append(");\n");
		}
		else
			classFormat.append("public Object "+ request.getServiceOperationName()+"Details ();\n");
	 classFormat.append("\n}");
		file = new File(request.getServicePath()+"\\service\\"+request.getServiceOperationName()+"Service.java");
		String contents = classFormat.toString();
		try {
			fop = new FileOutputStream(file);
			if (!file.exists()) {
				file.createNewFile();
			}
			byte[] contentInBytes = contents.getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	 }
	 
	public void generateServiceImpl(Request request) throws JSONException {
		JSONObject serviceInputParameters = null;
		FileOutputStream fop = null;
		File file;
		StringBuilder classFormat = new StringBuilder(); 
		classFormat.append("package;\n\n");
		classFormat.append("import com.sun.jersey.api.client.Client;\n");
		classFormat.append("import com.sun.jersey.api.client.ClientHandlerException;\n");
		classFormat.append("import com.sun.jersey.api.client.UniformInterfaceException;;\n");
		 classFormat.append("import com.sun.jersey.api.client.WebResource;\n");
		 classFormat.append("import com.sun.jersey.core.util.MultivaluedMapImpl;\n");
		classFormat.append("public class " + request.getServiceOperationName()+"ServiceImpl implements " + request.getServiceOperationName()+"Service {\n");
		if(request.getServiceInputParameters()!=null && !request.getServiceType().equals("QueryParam")){
			classFormat.append("public Object "+ request.getServiceOperationName()+"Details ( "+request.getServiceOperationName()+"Request request){\n");
		}
		else if(request.getServiceInputParameters()!=null && request.getServiceType().equals("QueryParam")){
			 serviceInputParameters = new JSONObject(request.getServiceInputParameters());
			classFormat.append("public Object "+ request.getServiceOperationName()+"Details (");
			for(int i=serviceInputParameters.names().length();i>0;i--){
				if(i-1==0)
					classFormat.append(serviceInputParameters.get(serviceInputParameters.names().getString(i-1)).getClass().toString().replaceAll("class", "")+" "+serviceInputParameters.names().getString(i-1));
				else
					classFormat.append(serviceInputParameters.get(serviceInputParameters.names().getString(i-1)).getClass().toString().replaceAll("class", "")+" "+serviceInputParameters.names().getString(i-1)+",");
			}
			classFormat.append("){\n");
		}
		else
			classFormat.append("public Object "+ request.getServiceOperationName()+"Details (){\n");
		classFormat.append("Object response = null;\n");
		classFormat.append("Client c = Client.create();\n");
		classFormat.append("WebResource resource = c.resource(\""+request.getServiceUrl()+"\");\n");
		if(request.getServiceInputParameters()!=null && request.getServiceType().equals("QueryParam")){
			classFormat.append("MultivaluedMap inputParams = new MultivaluedMapImpl();\n");
			for(int i=0;i<serviceInputParameters.names().length();i++){
				classFormat.append("inputParams.add(\""+serviceInputParameters.names().getString(i)+"\","+serviceInputParameters.names().getString(i)+");\n");	
			}
		}
		classFormat.append("response = resource");
		if(request.getServiceInputParameters()!=null && request.getServiceType().equals("QueryParam"))
			classFormat.append(".queryParams(inputParams)");
		if(request.getServiceProduces().contains("MediaType"))
			classFormat.append(".accept("+request.getServiceProduces()+")");
		if(request.getServiceConsumes().contains("MediaType"))
			classFormat.append(".type("+request.getServiceConsumes()+")");
		if(request.getServiceInputParameters()!=null && !request.getServiceType().equals("QueryParam")){
			if(request.getServiceProduces().contains("TEXT")){
				 if(request.getServiceMethod().equals("POST"))
					 classFormat.append(".post(String.class,request)");
				 else if(request.getServiceMethod().equals("PUT"))
					 classFormat.append(".put(String.class,request)");
				 else if(request.getServiceMethod().equals("DELETE"))
					 classFormat.append(".delete(String.class,request)");
			 }
			 else{
				 if(request.getServiceMethod().equals("POST"))
					 classFormat.append(".post(Object.class,request)");
				 else if(request.getServiceMethod().equals("PUT"))
					 classFormat.append(".put(Object.class,request)");
				 else if(request.getServiceMethod().equals("DELETE"))
					 classFormat.append(".delete(Object.class,request)");
			 }
		}
		 else{
			 if(request.getServiceProduces().contains("TEXT")){
				 switch(request.getServiceMethod()){
				 case "POST":
					 classFormat.append(".post(String.class)");
					 break;
				 case "PUT":
					 classFormat.append(".put(String.class)");
					 break;
				 case "DELETE":
					 classFormat.append(".delete(String.class)");
					 break;
				 case "GET":
					 classFormat.append(".get(String.class)");
					 break;
				default:
					 break;
				 }
				 }
				 else{
						 switch(request.getServiceMethod()){
						 case "POST":
							 classFormat.append(".post(Object.class)");
							 break;
						 case "PUT":
							 classFormat.append(".put(Object.class)");
							 break;
						 case "DELETE":
							 classFormat.append(".delete(Object.class)");
							 break;
						 case "GET":
							 classFormat.append(".get(Object.class)");
							 break;
						default:
							 break;
						 } 
				 }
		 }
		classFormat.append(";\n");
		 classFormat.append("\t\t\treturn response;\n\t}\n}\n");
		classFormat.append("}\n");
		classFormat.append("}\n");
		 file = new File(request.getServicePath()+"\\service\\"+request.getServiceOperationName()+"ServiceImpl.java");
			String content = classFormat.toString();
			try {
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}
				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
				fop.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (fop != null) {
						fop.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		 }
	 public void generatePojo(Request request) throws JSONException {
		 if(request.getServiceInputParameters()!=null)
		 {
		 System.out.println(request.getServiceInputParameters());
		 JSONObject json = new JSONObject(request.getServiceInputParameters());
		 String xml = XML.toString(json);
				System.out.println("request in xml format:" + xml);
				boolean flag = false;
				BufferedWriter bw = null;
				FileWriter fw = null;
				String xml1=xml.replaceFirst("<", "<root><");
				 String xml2=xml1.replaceAll("><", ">\n<");
				 int ind = xml2.lastIndexOf(">");
				 String requestFinal = null;
				if( ind>=0 )
					requestFinal = new StringBuilder(xml2).replace(ind, ind+1,">\n</root>").toString();
				 System.out.println(requestFinal);
				 System.out.println("h33i********************");
			System.out.println("input:" + requestFinal);
			
			File stockFile = new File("sample.xml");

			try {
				flag = stockFile.createNewFile();
				FileWriter writer = new FileWriter(stockFile);
				writer.write(requestFinal);
				writer.flush();
				writer.close();

			} catch (IOException ioe) {
				System.out.println("Error while Creating File in Java" + ioe);
			} finally {
				try {
					if (bw != null)
						bw.close();
					if (fw != null)
						fw.close();
				} catch (IOException ex) {

					ex.printStackTrace();
				}
			}
			System.out.println("file -" + stockFile.getPath() + " created ");
			XmlToPojo(request);
		}
		
	 }

		/**
		 * read from file
		 */
		public void XmlToPojo(Request request) {
			File xmlInFile = new File("sample.xml");
			Document doc = null;
			String rootElement = request.getServiceOperationName()+"Request";
			try {
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
				doc = dBuilder.parse(xmlInFile);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (doc.hasChildNodes()) {

				getNodeDetails(doc.getChildNodes().item(0).getChildNodes(),
						rootElement,request);
			}

		}

		/**
		 * generate xml to pojo
		 * @param childNodes
		 * @param rootElement
		 */
		private static void getNodeDetails(NodeList childNodes, String rootElement,Request request) {
			StringBuilder classFormat = new StringBuilder();
			StringBuilder getterAndSetter = new StringBuilder();
			classFormat.append("package ;\n");
			classFormat.append("\n\nimport javax.xml.bind.annotation.XmlRootElement;\n");
			classFormat.append("import javax.xml.bind.annotation.XmlAttribute;\n");
			classFormat.append("import javax.xml.bind.annotation.XmlElement;\n");
			classFormat.append("import java.util.List;\n");
			classFormat
					.append("import com.fasterxml.jackson.annotation.JsonProperty;\n\n");
			classFormat.append("@XmlRootElement(name=\"" + rootElement + "\")\n");
			classFormat.append("public class " + rootElement + " {\n");
			boolean isListPresent = false;
			if (childNodes.getLength() / 2 > 1) {
				//node does not has child node
				if ((childNodes.item(1).getNodeName().equals(childNodes.item(3)
						.getNodeName()))) {
					isListPresent = true;
				}

			}
			//node has child node
			for (int i = 1; i < childNodes.getLength() && !isListPresent; i += 2) {

				Node node = childNodes.item(i);
				int noOfChild = node.getChildNodes().getLength() / 2; // total no of
																		// child
																		// nodes

				if ((noOfChild > 0 || node.getAttributes().getLength() > 0)
						&& isListPresent == false) {

					classFormat.append("	private " + node.getNodeName() + " "
							+ node.getNodeName().toLowerCase() + ";\n");
					getterAndSetter.append("\n");
					getterAndSetter.append("	@XmlElement(name=\""
							+ node.getNodeName() + "\")\n");
					getterAndSetter.append("	@JsonProperty(\"" + node.getNodeName()
							+ "\")\n");
					getterAndSetter.append("	public " + node.getNodeName() + " get"
							+ node.getNodeName() + "() {\n");
					getterAndSetter.append("		return "
							+ node.getNodeName().toLowerCase() + ";\n");
					getterAndSetter.append("	}\n\n");

					getterAndSetter.append("	public void set" + node.getNodeName()
							+ "(" + node.getNodeName() + " "
							+ node.getNodeName().toLowerCase() + ") {\n");
					getterAndSetter.append("		this."
							+ node.getNodeName().toLowerCase() + " = "
							+ node.getNodeName().toLowerCase() + ";\n");
					getterAndSetter.append("	}\n\n");

				} else {

					classFormat.append("	private String " + node.getNodeName()
							+ ";\n");
					getterAndSetter.append("\n");
					getterAndSetter.append("	@XmlElement(name=\""
							+ node.getNodeName() + "\")\n");
					getterAndSetter.append("	@JsonProperty(\"" + node.getNodeName()
							+ "\")\n");
					getterAndSetter.append("	public String" + " get"
							+ node.getNodeName() + "() {\n");
					getterAndSetter
							.append("		return " + node.getNodeName() + ";\n");
					getterAndSetter.append("	}\n\n");

					getterAndSetter.append("	public void set" + node.getNodeName()
							+ "(String" + " " + node.getNodeName().toLowerCase()
							+ ") {\n");
					getterAndSetter.append("		" + node.getNodeName() + " = "
							+ node.getNodeName().toLowerCase() + ";\n");
					getterAndSetter.append("	}\n\n");
				}

			}

			if (isListPresent) {
				Node node = childNodes.item(1);
				classFormat.append("	private List<" + node.getNodeName() + "> "
						+ node.getNodeName().toLowerCase() + "List;\n");
				getterAndSetter.append("\n");
				getterAndSetter.append("	@XmlElement(name=\"" + node.getNodeName()
						+ "\")\n");
				getterAndSetter.append("	@JsonProperty(\"" + node.getNodeName()
						+ "\")\n");
				getterAndSetter.append("	public List<" + node.getNodeName()
						+ "> get" + node.getNodeName() + "() {\n");
				getterAndSetter.append("		return "
						+ node.getNodeName().toLowerCase() + "List;\n");
				getterAndSetter.append("	}\n\n");

				getterAndSetter.append("	public void set" + node.getNodeName()
						+ "(List<" + node.getNodeName() + "> "
						+ node.getNodeName().toLowerCase() + ") {\n");
				getterAndSetter.append("		this." + node.getNodeName().toLowerCase()
						+ "List = " + node.getNodeName().toLowerCase() + ";\n");
				getterAndSetter.append("	}\n\n");
			}

			classFormat.append(getterAndSetter.toString());
			classFormat.append("}");

			// save String to file
			FileOutputStream fop = null;
			File file;

			file = new File(request.getServicePath()+"\\pojoRequest\\" + rootElement+ ".java");
			String content = classFormat.toString();
			try {
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}
				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
				fop.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (fop != null) {
						fop.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node = childNodes.item(i);
				if ((node.hasChildNodes() && node.getChildNodes().getLength() / 2 >= 1)) {

					getNodeDetails(node.getChildNodes(), node.getNodeName(),request);
					if (isListPresent) {
						break;
					}
				}

				if (node.hasAttributes()) {
					System.out.println(node.getNodeName() + " "
							+ node.hasAttributes());
					getAttrDetails(node.getAttributes(), node.getNodeName(),request);
					if (isListPresent) {
						break;
					}
				}
			}

		}

		private static void getAttrDetails(NamedNodeMap attributes, String nodeName,Request request) {
			System.out.println("in attr");
			StringBuilder classFormat = new StringBuilder();
			StringBuilder getterAndSetter = new StringBuilder();
			classFormat.append("package ;\n");
			classFormat
					.append("\n\nimport javax.xml.bind.annotation.XmlRootElement;\n");
			classFormat.append("import javax.xml.bind.annotation.XmlAttribute;\n");
			classFormat.append("import javax.xml.bind.annotation.XmlElement;\n");
			classFormat.append("import java.util.List;\n");
			classFormat
					.append("import com.fasterxml.jackson.annotation.JsonProperty;\n\n");
			classFormat.append("@XmlRootElement(name=\"" + nodeName + "\")\n");
			classFormat.append("public class " + nodeName + " {\n");
			for (int j = 0; j < attributes.getLength(); j++) {
				Node attr = attributes.item(j);
				classFormat.append("	private String " + attr.getNodeName() + ";\n");
				getterAndSetter.append("\n");
				getterAndSetter.append("	@XmlAttribute(name=\""
						+ attr.getNodeName() + "\")\n");
				getterAndSetter.append("	@JsonProperty(\"" + attr.getNodeName()
						+ "\")\n");
				getterAndSetter.append("	public String" + " get"
						+ attr.getNodeName() + "() {\n");
				getterAndSetter.append("		return " + attr.getNodeName() + ";\n");
				getterAndSetter.append("	}\n\n");

				getterAndSetter.append("	public void set" + attr.getNodeName()
						+ "(String" + " " + attr.getNodeName().toLowerCase()
						+ ") {\n");
				getterAndSetter.append("		" + attr.getNodeName() + " = "
						+ attr.getNodeName().toLowerCase() + ";\n");
				getterAndSetter.append("	}\n\n");

			}
			classFormat.append(getterAndSetter.toString());
			classFormat.append("}");

			FileOutputStream fop = null;
			File file;
			file = new File(request.getServicePath()+"\\pojoRequest\\" + nodeName+ ".java");
			String content = classFormat.toString();
			try {
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}
				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
				fop.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (fop != null) {
						fop.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		
}
