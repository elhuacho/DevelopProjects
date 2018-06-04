package httpclient;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import org.apache.http.client.ClientProtocolException;
import org.junit.Test;


public class AutomateWebServiceMain {
	private static Scanner sc;
	
	@Test
	public void main() throws UnsupportedEncodingException, ClientProtocolException, IOException{
		sc = new Scanner(System.in);
		System.out.println("--Método GET--");
		System.out.println("Ingrese localización: ");
		String location = sc.next();
		System.out.println("Ingrese radio: ");
		String radius = sc.next();
		System.out.println("Ingrese tipo: ");
		String types = sc.next();
		System.out.println("Ingrese sensor: ");
		String sensor = sc.next();
		System.out.println("Ingrese key: ");
		String key = sc.next();
		AutomateWebService.get(location, radius, types, sensor, key);
   
	}

}
