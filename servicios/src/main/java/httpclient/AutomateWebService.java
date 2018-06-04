package httpclient;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class AutomateWebService {
	
	public static String get(String location, String radius, String types, String sensor, String key) {
//		String location = "51.503186,-0.126446";
//		String radius = "5000";
//		String types = "food";
//		String sensor = "false";
//		String key = "AIzaSyBpVWqSvVaXwPqjp4wKx43xRaETpSBG_hA";
		String status = "";
		String respuesta = "";
		
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet("https://maps.googleapis.com/maps/api/place/search/json?location="+location+"&radius="+radius+"&types="+types+"&sensor="+sensor+"&key="+key+"");
			HttpResponse response1 = httpClient.execute(getRequest);
			status = response1.getStatusLine().toString();
			System.out.println(status);
			HttpEntity entity1 = response1.getEntity();
			respuesta = EntityUtils.toString(entity1);
			System.out.println(respuesta);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return status + "\n" + respuesta;
	}
	
}
