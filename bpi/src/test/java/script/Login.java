package script;

import com.everis.*;

import web.TecladoWeb;

public class Login {
	private static String msjFinal = "";
	private static Element txtTarjetaCredito = new Element("name", "creditcard");
	private static Element txtNumero = new Element("name", "idnumber");
	private static Element rdbTipoDocumento = new Element("xpath", "//*[@id='app']/div[2]/div/div[2]/div[1]/div/div[1]");
	private static Element claveWeb = new Element("name", "valueToShow");
	private static Element btnIngresar = new Element("xpath", "//*[@id='app']/div[2]/div/div[6]/a");
	private static Element msjValidacion = new Element("xpath", "//*[@id='app']/div[3]/div/div/div/div/div[3]/p");
	private	static Element btnSi = new Element("xpath", "//*[@id='app']/div[3]/div/div/div/div/div[4]/div/div[1]/button");
	private static Element btnAceptar = new Element("xpath", "//*[@id='app']/div[3]/div/div/div/div/div[4]/div/div/button");
	private static Element btnCerrarSesion = new Element("xpath", "//*[@id='app']/div/div/header/div/ul/li[3]/a");
	private static Element cabezeraPerfil = new Element("xpath", "//*[@id='app']/div/div/header/div/div/div[2]/div");
	
	public static Boolean ingresarSesion() throws Exception { 
		msjFinal = "";
		EFA.executeAction(Action.Navigate, GlobalData.getData("viURL"));
		EFA.executeAction(Action.SendKeys, txtTarjetaCredito, GlobalData.getData("viTarjetaCredito"));
		EFA.executeAction(Action.Click, rdbTipoDocumento);
		Element tipoDocumento = new Element("xpath", "//*[@id='app']/div[1]/div/ul/li[*]/a/div/div[contains(text(), '"+GlobalData.getData("viTipoDocumento")+"')]");
		EFA.executeAction(Action.Click, tipoDocumento);		
		EFA.executeAction(Action.SendKeys, txtNumero, GlobalData.getData("viNumero"));		
		EFA.executeAction(Action.Click, claveWeb);
			
		TecladoWeb.ingresarClaveWeb(GlobalData.getData("viClaveWeb"));
		
		EFA.executeAction(Action.Click, btnIngresar);	
		
		String mensajeValidacion = EFA.executeAction(Action.GetText, msjValidacion).toString();
		msjFinal = mensajeValidacion;
		
		Thread.sleep(2000);
		if(Boolean.parseBoolean(EFA.executeAction(Action.IsElementPresent, btnSi).toString())){
			EFA.executeAction(Action.Click, btnSi);
		}else {
			if(!Boolean.parseBoolean(EFA.executeAction(Action.IsElementPresent, cabezeraPerfil).toString())){
				EFA.executeAction(Action.Click, btnAceptar);
				EFA.executeAction(Action.Refresh, null);
				return false;
			}
		} 
		return true;
	}
	
	public static void cerrarSesion() throws Exception {
		EFA.executeAction(Action.Click, btnCerrarSesion);
	}
	
	public static String getResult() throws Exception {
		return msjFinal.isEmpty()?"Correcto":msjFinal;
	}
}
