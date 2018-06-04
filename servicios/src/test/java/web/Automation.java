package web;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TestName;

import com.everis.EFA;
import com.everis.ExecutionInfo;
import com.everis.data.DataLoad;

public class Automation extends EFA{


	@Rule
	public TestName currentTest = new TestName();

	/**
	 * Load data from input file or data base
	 * @return List with data
	 * @throws Exception
	 */
	public static List<Object> loadData() throws Exception {
		// Capture test name and test suite
		String[] fullClassName = new Throwable().getStackTrace()[1].getClassName().toString().split("\\W");
		ExecutionInfo.setTestSuite(fullClassName[fullClassName.length - 2]);
		ExecutionInfo.setTestName(fullClassName[fullClassName.length - 1]);
		// Load data used on script
		return Arrays.asList(new DataLoad().load());
	}

	/**
	 * Scenario - Postcondition for the test
	 * @throws Exception
	 */
	@After
	public void afterRunTest() throws Exception {
        EFA.finishExecution();
        
	}


}
