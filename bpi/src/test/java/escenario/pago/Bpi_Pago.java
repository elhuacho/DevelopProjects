package escenario.pago;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.everis.EFA;
import com.everis.ExecutionInfo;
import com.everis.GlobalData;
import com.everis.data.DataDictionary;
import com.everis.webautomation.ExecutionWrapper;

//import web.Automation;
import script.Pago;

@RunWith(Parameterized.class)
public class Bpi_Pago extends ExecutionWrapper {
	
	public Bpi_Pago(String executionName, DataDictionary data){
		ExecutionInfo.setExecutionName(executionName);
		GlobalData.load(data);
	}
	
	@Parameters(name = "{0}")
	public static List<Object> loadTestData() throws Exception {
		return loadData();
	}
	
	@Before
	public void beforeTest() throws Exception {
		EFA.loadExecutionInfo();
	}
	
	@Test
	public void script(){
		String output = "";
		try {
			Pago.pago();
			output = Pago.getResult();
		} catch (Exception e) {
			output = "Error: " + e.getMessage();
		} finally {
			ExecutionInfo.setResult(output);
		}
	}
}
