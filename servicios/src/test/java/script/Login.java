package script;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.everis.ExecutionInfo;
import com.everis.GlobalData;
import net.minidev.json.JSONObject;

public class Login {
	
	private static String msjFinal = "";
	private static String cv_pathEvidence = "";
	private static String evidenceName = "N/A";
	private static BufferedWriter writer;
	
	public static String getOutputDirectory() {
	    String outputDirectory = new File("output").getAbsoluteFile() + "\\";
	    if (!new File(outputDirectory).exists()) {
	      new File(outputDirectory).mkdir();
	    } 
	    return outputDirectory;
	}
	
	public static String getTestEvidence() throws IOException {
		if (cv_pathEvidence.equals(""))
		{
		  evidenceName = getOutputDirectory();
		  evidenceName = evidenceName + ExecutionInfo.getExecutionTimestamp() + "/";
		  evidenceName = evidenceName + ExecutionInfo.getTestSuite() + "/";
		  evidenceName = evidenceName + ExecutionInfo.getTestName() + "/";
		  evidenceName = evidenceName + ExecutionInfo.getExecutionName() + "/";
		  evidenceName = evidenceName + "evidence/";	  
		}
		else
		{
		  evidenceName = cv_pathEvidence + "/";
		  evidenceName = evidenceName + ExecutionInfo.getExecutionTimestamp() + "/";
		  evidenceName = evidenceName + ExecutionInfo.getTestSuite() + "/";
		  evidenceName = evidenceName + ExecutionInfo.getTestName() + "/";
		  evidenceName = evidenceName + ExecutionInfo.getExecutionName() + "/";
		  evidenceName = evidenceName + "evidence/";
		} 		

	    return evidenceName;
	}

	public static void request() throws UnsupportedEncodingException, ClientProtocolException, IOException{
		msjFinal = "";
		JSONObject params = new JSONObject();
		params.put("application", GlobalData.getData("viApplication")); 
		params.put("userName", GlobalData.getData("viUserName")); 
		params.put("deviceToken", GlobalData.getData("viDeviceToken")); 
		params.put("platform", GlobalData.getData("viPlatform")); 
		params.put("password", GlobalData.getData("viPassword")); 
		String jsonData = params.toString();
		
		HttpPost post = new HttpPost(GlobalData.getData("viURL"));
		post.addHeader("Content-Type", "application/json");
		post.addHeader("Host", "api.us.apiconnect.ibmcloud.com");
		post.addHeader("X-IBM-Client-Id", "b7b2fa12-d5ed-4c35-a121-4c9b4d7637bc");
		post.addHeader("X-Forwarded-Proto", "https");
		post.setEntity(new StringEntity(jsonData));
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
		CloseableHttpResponse response = client.execute(post);
		
		String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
		Header[] responseHeader = response.getAllHeaders();
		
		for (Header header : responseHeader) {
			System.out.println(header);
		}
				
		if(statusCode.equals("201")) {
			System.out.println("Status code: " + statusCode);
		} else {
			msjFinal = "Status code: " + statusCode;
		}
		
		String respuesta = EntityUtils.toString(response.getEntity());
		ObjectMapper mapper = new ObjectMapper();
		Object objBody = mapper.readValue(respuesta, Object.class);
		String responseBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objBody);
	
		if (!respuesta.isEmpty()) {
			System.out.println("Body response: \n" + responseBody);
		} else {
			msjFinal = "Problemas con el servicio.";
		}

	    new File(getTestEvidence()).mkdirs();
		
		writer = new BufferedWriter(new FileWriter(getTestEvidence() + "response.json"));
		writer.write(responseBody);
		writer.close();
		
	    client.close();

	}

	public static String getResult() throws Exception{
		return msjFinal.isEmpty()?"Correcto":msjFinal;
	}
}
