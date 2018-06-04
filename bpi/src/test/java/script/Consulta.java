package script;

import java.util.concurrent.TimeUnit;

import com.everis.*;

public class Consulta {
	private static String msjFinal = "";
	private static Element consultaCuenta = new Element("xpath", "//*[@id='app']/div/div/section/div/div/div[1]/div[2]/div[1]/div/div/div/div[1]/div");
	
	public static void consultarCuenta() throws Exception {
		msjFinal = "";
		if(Login.ingresarSesion().equals(true)){
			EFA.executeAction(Action.Click, consultaCuenta);
			EFA.cv_driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Login.cerrarSesion();
		}
	}
	
	public static String getResult() throws Exception {
		return msjFinal.isEmpty()?"Correcto":msjFinal;
	}
}
