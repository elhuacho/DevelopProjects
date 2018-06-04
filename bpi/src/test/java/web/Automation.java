package web;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.everis.Action;
import com.everis.EFA;
import com.everis.ExecutionInfo;
import com.everis.Manager;
import com.everis.data.DataLoad;
public class Automation {
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
        return Arrays.asList(new web.DataLoad().load());
    }
    /**
     * Setup log file
     * @throws Exception
     */
    @Before
    public void beforeRunTest() throws Exception {
    }
    /**
     * Initialize the framework
     * @throws Exception
     */
    @BeforeClass
    public static void beforeExecution() throws Exception {
        // Start driver
    	Manager.loadDriver();
        
    }
    /**
     * Scenario - Postcondition for the test
     * @throws Exception
     */
    @After
    public void afterRunTest() throws Exception {
        EFA.executeAction(Action.ClearSession, null);
        EFA.finishExecution();
         
    }
    /**
     * Execution - Postcondition for the execution
     * @throws Exception
     */
    @AfterClass
    public static void afterExecution() throws Exception {
        // Start driver
        Manager.finishDriver();
    }
}