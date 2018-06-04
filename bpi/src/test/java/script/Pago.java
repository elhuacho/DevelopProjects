package script;

import org.openqa.selenium.JavascriptExecutor;

//import org.openqa.selenium.JavascriptExecutor;

import com.everis.*;

public class Pago {
	private static String msjFinal = "";
	private static Element btnPagar = new Element("xpath", "//*[@id='app']/div/div/section/div/div/div[3]/div[2]/div[1]/div/div/div[1]/div[2]/div/div/div[1]/div/div/div[3]/div/button");
	private static Element cboEmpresa = new Element("id", "companySlc");
	private static Element cboServicio = new Element("id", "serviceSlc");
	private static Element txtApellidoCodigo = new Element("id", "dynamicLabel");
	private static Element btnBuscar = new Element("xpath", "//*[@id='app']/div/div/section/div/div/div[3]/div[2]/div[1]/div/div/div[1]/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[4]/button");
	private static Element cboMoneda = new Element("id", "exchangeSlc");
	private static Element txtMonto = new Element("name", "inpAmountLoan");
	private static Element cboCuenta = new Element("name", "selectChargeAccountList");
	private static Element btnSiguiente = new Element("xpath", "//*[@id='app']/div/div/section/div/div/div[3]/div[2]/div[1]/div/div/div[1]/div[2]/div/div/div[2]/div/div/div/div/div[2]/div/div[2]/button");
		
	public static void pago() throws Exception {
		msjFinal = "";
		if (Login.ingresarSesion().equals(true)) {	
			Thread.sleep(4000);
			Element rdbPago = new Element("xpath", "//div[@id='typePayment']/label[*]/span[1][contains(text(),'"+GlobalData.getData("viOperacionPago")+"')]");
			EFA.executeAction(Action.Click, rdbPago);
			((JavascriptExecutor)EFA.cv_driver).executeScript("windows.scrollBy(0, 250)");
			EFA.executeAction(Action.Click, btnPagar);	
			EFA.executeAction(Action.Click, cboEmpresa);
			Element opcEmpresa = new Element("xpath", "//*[@id='app']/div/div/section/div/div/div[3]/div[2]/div[1]/div/div/div[1]/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[1]/div/div[1]/ul/li[contains(text(), '"+GlobalData.getData("viEmpresa")+"')]");
			EFA.executeAction(Action.Click, opcEmpresa);
			EFA.executeAction(Action.Click, cboServicio);
			Element opcServicio = new Element("xpath", "//*[@id='app']/div/div/section/div/div/div[3]/div[2]/div[1]/div/div/div[1]/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[2]/div/div[1]/ul/li[contains(text(), '"+GlobalData.getData("viServicio")+"')]");
			EFA.executeAction(Action.Click, opcServicio);
			EFA.executeAction(Action.SendKeys, txtApellidoCodigo, GlobalData.getData("viApellidoCodigo"));
			EFA.executeAction(Action.Click, btnBuscar);
			EFA.executeAction(Action.Click, cboMoneda);
			Element opcMoneda = new Element("xpath", "//*[@id='app']/div/div/section/div/div/div[3]/div[2]/div[1]/div/div/div[1]/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[5]/div/div[1]/div/div[1]/div/div[1]/ul/li[contains(text(), '"+GlobalData.getData("viMoneda")+"')]");
			EFA.executeAction(Action.Click, opcMoneda);
			EFA.executeAction(Action.SendKeys, txtMonto, GlobalData.getData("viMonto"));
//			((JavascriptExecutor)EFA.cv_driver).executeScript("windows.scrollBy(0, 250)");
			EFA.executeAction(Action.Click, cboCuenta);
			Element opcCuenta = new Element("xpath", "//*[@id='selectChargeAccountList']/div[1]/div/div[1]/ul/li[*][contains(text(), '"+GlobalData.getData("viCuenta")+"')]");
			EFA.executeAction(Action.Click, opcCuenta);
			EFA.executeAction(Action.Click, btnSiguiente);
			Login.cerrarSesion();	
		}
	}
	
	public static String getResult() throws Exception {
		return msjFinal.isEmpty()?"Correcto":msjFinal;
	}

}
