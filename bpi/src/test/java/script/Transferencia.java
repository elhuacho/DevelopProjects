package script;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.everis.*;

public class Transferencia {
	private static String msjFinal = "";
//	private static Element selTransferirDestino = new Element("name", "typeTransfersCmb");
	//private static Element selTransferirDestino = new Element("xpath", "//input[@name='typeTransfersCmb']");
	private static Element selTransferirDestino = new Element("xpath", "/html/body/div[@id='app']/div/div/section[@class='icontainer icontainer__body']/div/div[@class='icontainer home']/div[@class='blockTitleTop transfers']/div[@class='blockTitleTop__wrapper']/div[@class='blockTitleTop__container']/div/div[@class='mol_step-wizard']/div[@class='step-wizard__mask']/div[@class='step-wizard__wrapper']/div/div[@class='mol_step-wizard-content right visible']/div[@class='mol_step-wizard-content__wrapper']/div[@class='content--wrapper']/div[@class='Form__group mdl-textfield mdl-js-textfield mdl-textfield--floating-label is-upgraded']/div[@class='ibk-combo ibk-combo--default']");
	private static Element selCuentaTarjetaCargo = new Element("name", "selectChargeAccountList");	
	private static Element selCuentaDestino = new Element("name", "selectDestinyAccountList");
	private static Element selMoneda = new Element("name", "currency");
	private static Element txtMonto = new Element("name", "amount");
	private static Element btnTransferir = new Element("xpath", "//*[@id='app']/div/div/section/div/div/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div/div[1]/div/div[2]/div/button");
	private static Element btnConfirmar = new Element("xpath", "//*[@id='app']/div/div/section/div/div/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div/div[2]/div/div/div[2]/div[2]/button");
	private static Element btnRealizarOtraOperacion = new Element("xpath", "//*[@id='app']/div/div/section/div/div/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div/div[3]/div/div/div[2]/div[3]/button");
//	private static Element load = new Element("xpath", "//*[@id='app']/div/div[@class='lock']/div[@class='mask mask--full']/div[@class='loading-small']");
		
	public static void transferirCuentasPropias() throws Exception {
		msjFinal = "";
		if(Login.ingresarSesion().equals(true)){
					
			if((Boolean)EFA.executeAction(Action.WaitForElementPresent, selTransferirDestino)){
				
				Thread.sleep(2000);
				EFA.executeAction(Action.Click, selTransferirDestino);	
				WebElement contenedorTransferirDestino = EFA.cv_driver.findElement(By.xpath("//*[@id='app']/div/div/section/div/div/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div/div[1]/div/div[1]/div[1]/div/div[1]/ul"));
				List<WebElement> listaTransferirDestino = contenedorTransferirDestino.findElements(By.xpath(".//*[name()='li']"));
				for (int i = 1; i <= listaTransferirDestino.size(); i++) {
					String valorXpath = "//*[@id='app']/div/div/section/div/div/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div/div[1]/div/div[1]/div[1]/div/div[1]/ul/li["+i+"]";
					Element opcion = new Element("xpath", valorXpath);
					String valorTransferirDestino = EFA.executeAction(Action.GetText, opcion).toString();
					if (GlobalData.getData("viTransferirDestino").equals(valorTransferirDestino)) {
						EFA.executeAction(Action.Click, opcion);
						break;
					}
				}
							
				EFA.executeAction(Action.Click, selCuentaTarjetaCargo);
				WebElement contenedorCuentaTarjetaCargo = EFA.cv_driver.findElement(By.xpath("//*[@id='app']/div/div/section/div/div/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div/div[1]/div/div[1]/div[2]/div/div[1]/ul"));
				List<WebElement> listaCuentaTarjetaCargo = contenedorCuentaTarjetaCargo.findElements(By.xpath(".//*[name()='li']"));
				for (int i = 1; i <= listaCuentaTarjetaCargo.size() ; i++) {
					String valorXpath = "//*[@id='app']/div/div/section/div/div/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div/div[1]/div/div[1]/div[2]/div/div[1]/ul/li["+i+"]";
					Element opcion = new Element("xpath", valorXpath);
					String valorCuentaTarjetaCargo = EFA.executeAction(Action.GetText, opcion).toString();
					if (GlobalData.getData("viCuentaTarjetaCargo").equals(valorCuentaTarjetaCargo)) {
						EFA.executeAction(Action.Click, opcion);
						break;
					}	
				}
				
				EFA.executeAction(Action.Click, selCuentaDestino);
				WebElement contenedorCuentaDestino = EFA.cv_driver.findElement(By.xpath("//*[@id='app']/div/div/section/div/div/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div/div[1]/div/div[1]/div[3]/div/div/div[1]/ul"));
				List<WebElement> listaCuentaDestino = contenedorCuentaDestino.findElements(By.xpath(".//*[name()='li']"));
				for (int i = 1; i <= listaCuentaDestino.size() ; i++) {
					String valorXpath = "//*[@id='app']/div/div/section/div/div/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div/div[1]/div/div[1]/div[3]/div/div/div[1]/ul/li["+i+"]";
					Element opcion = new Element("xpath", valorXpath);
					String valorCuentaDestino = EFA.executeAction(Action.GetText, opcion).toString();
					if (GlobalData.getData("viCuentaDestino").equals(valorCuentaDestino)) {
						EFA.executeAction(Action.Click, opcion);
						break;
					}	
				}
				
				EFA.executeAction(Action.Click, selMoneda);
				WebElement contenedorMoneda = EFA.cv_driver.findElement(By.xpath("//*[@id='app']/div/div/section/div/div/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div/div[1]/div/div[1]/div[6]/div/div[1]/div/div[1]/ul"));
				List<WebElement> listaMoneda = contenedorMoneda.findElements(By.xpath(".//*[name()='li']"));
				for (int i = 1; i <= listaMoneda.size() ; i++) {
					String valorXpath = "//*[@id='app']/div/div/section/div/div/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div/div[1]/div/div[1]/div[6]/div/div[1]/div/div[1]/ul/li["+i+"]";
					Element opcion = new Element("xpath", valorXpath);
					String valorMoneda = EFA.executeAction(Action.GetText, opcion).toString();
					if (GlobalData.getData("viMoneda").equals(valorMoneda)) {
						EFA.executeAction(Action.Click, opcion);
						break;
					}	
				}
				
				
				EFA.executeAction(Action.Click, txtMonto);
				EFA.executeAction(Action.SendKeys, txtMonto, GlobalData.getData("viMonto"));
				
				EFA.executeAction(Action.Click, btnTransferir);
				EFA.executeAction(Action.Click, btnConfirmar);
				EFA.executeAction(Action.Click, btnRealizarOtraOperacion);
				
				((JavascriptExecutor)EFA.cv_driver).executeScript("scroll(0, -250);");
				
				EFA.cv_driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				Login.cerrarSesion();	
			}
		}
	}
	
	public static String getResult() throws Exception {
		return msjFinal.isEmpty()?"Correcto":msjFinal;
	}

}
