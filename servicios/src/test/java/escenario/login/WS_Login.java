package escenario.login;

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

import script.Login;
import web.Automation;

@RunWith(Parameterized.class)
public class WS_Login extends Automation{
	public WS_Login(String executionName, DataDictionary data){
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
			Login.request();	
			output = Login.getResult();
		} catch (Exception e) {
			output = "Error: " + e.getMessage();
		} finally {
			ExecutionInfo.setResult(output);
		}
	}
}
