package escenario.consulta;

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

import script.Consulta;
//import web.Automation;

@RunWith(Parameterized.class)
public class Bpi_Consulta extends ExecutionWrapper {
	
	public Bpi_Consulta(String executionName, DataDictionary data){
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
			Consulta.consultarCuenta();
			output = Consulta.getResult();
		} catch (Exception e) {
			output = "Error: " + e.getMessage();
		} finally {
			ExecutionInfo.setResult(output);
		}
	}
}
