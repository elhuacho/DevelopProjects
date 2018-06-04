package web;

import com.everis.ExecutionInfo;
import com.everis.Language;
import com.everis.LogMessage;
import com.everis.Utils;
import com.everis.data.*;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataLoad {
	private HashMap<String, String> dictionary;
	private int iTotalRows = 0;
	private int iTotalCells = 0;
	private InputType input;

	public DataLoad() throws Exception {
		String inputType = System.getProperty("inputType");
		if ((inputType == null) || (inputType == "")) {
			throw new Exception(LogMessage.get("inputType"));
		}
		try {
			this.input = InputType.valueOf(inputType.toUpperCase());
		} catch (Exception e) {
			throw new Exception("Tipo de entrada de dados n?o cadastrada.");
		}
	}

	public Object[] load() throws Exception {
		Object[] objReturn = null;
		switch (this.input) {
		case CSV:
			objReturn = null;
			break;
		case JSON:
			objReturn = null;
			break;
		case YAML:
			objReturn = null;
			break;
		case XML:
			objReturn = null;
			break;
		case EXCEL:
			objReturn = loadExcelData();
			break;
		case SQLSERVER:
			objReturn = loadSQLData();
			break;
		default:
			objReturn = null;
		}
		return objReturn;
	}

	private Object[] loadExcelData() throws Exception {
		DataExcel excel = new DataExcel(new File("data").getAbsoluteFile() + "/" + ExecutionInfo.getTestSuite() + ".xlsx");
		excel.open();

		excel.readSheet(ExecutionInfo.getTestName());

		this.iTotalRows = excel.countRows();
		List<Object[]> listReturn = new ArrayList();

		this.iTotalCells = excel.countCells();

		int columnRunTest = 1;
		while (!excel.readCell(1, columnRunTest).equalsIgnoreCase(Language.get("RunTest?"))) {
			if (excel.readCell(1, columnRunTest).isEmpty()) {
				throw new Exception("Column " + Language.get("RunTest?") + " not found!");
			}
			columnRunTest++;
		}
		for (int iRow = 1; iRow < this.iTotalRows; iRow++) {
			this.dictionary = new HashMap();
			if (Utils.isTrue(excel.readCell(iRow + 1, columnRunTest))) {
				for (int iCell = 1; iCell <= this.iTotalCells; iCell++) {
					this.dictionary.put(excel.readCell(1, iCell), excel.readCell(iRow + 1, iCell));
				}
				this.dictionary.put("index", String.valueOf(iRow + 1));

				listReturn.add(new Object[] { this.dictionary.get(Language.get("TestCase")), new DataDictionary(this.dictionary) });
			}
		}
		Object[] objectReturn = new Object[listReturn.size()];
		objectReturn = listReturn.toArray(objectReturn);
		return objectReturn;
	}

	private Object[] loadSQLData() throws Exception {
	    ArrayList<String> idsExecucao = new ArrayList();
	    List<Object[]> listReturn = new ArrayList();
	    Json json = new Json();
	    
	    String checkRun = "";
	    Database database = new Database();
	    
	    System.out.println(ExecutionInfo.getTestName());
	    database.open();
	    
	    String idCasoStr = database.executeAndReturnFirstResult("Select ID_Caso from CasoTeste Where Nome_Caso_Teste = '" + 
	    	      ExecutionInfo.getTestName() + "'");
	    int idCaso = -1;
	    if(!idCasoStr.equals("DataNotFound")){
	    	idCaso=Integer.parseInt(idCasoStr);
	    }
	    
	    String cNombrePC = InetAddress.getLocalHost().getHostName();
	   
	    idsExecucao = database.executeAndReturnAllResults("Select E.ID_Execucao from Execucao E JOIN Ejecucion E1 ON E.nEjeCodigo=E1.nEjeCodigo Where "
	    		+ "ID_Caso = '" + idCaso + "' AND E1.cNombrePc='"+cNombrePC+"' AND E1.dEjeFechaEjecucion=CAST(GETDATE() AS DATE) "
	    				+ " AND E1.cEstadoEjecucion='P'");
	    if (System.getProperty("checkRun") != null) {
	      checkRun = System.getProperty("checkRun");
	    }
	    for (int i = 0; i < idsExecucao.size(); i++)
	    {
	      String statusExecucao = database.executeAndReturnFirstResult("Select Status_Execucao from Execucao Where ID_Execucao = " + 
	        (String)idsExecucao.get(i) + "");
	      boolean run = true;
	      /*if (((checkRun.equalsIgnoreCase("")) || (checkRun.equalsIgnoreCase("TRUE"))) && 
	        (statusExecucao != null)) {
	        run = false;
	      }*/
	      if (run)
	      {
	        String jsonData = database.getDataJson(Integer.parseInt((String)idsExecucao.get(i)));
	        json.csSetData(jsonData);
	        String testCaseName = database.executeAndReturnFirstResult("Select Nome_Caso_Teste from Dados Inner Join Execucao On Dados.ID_Dados = Execucao.ID_Dados Where ID_Execucao = " + 
	          (String)idsExecucao.get(i));
	        this.dictionary = new HashMap();
	        this.dictionary = json.getJsonMap();
	        this.dictionary.put("Test Case", testCaseName + " Execucao " + (String)idsExecucao.get(i));
	        String expectedResult = database.executeAndReturnFirstResult("Select Resultado_Esperado from Execucao Inner Join Dados ON Dados.ID_Dados = Execucao.ID_Dados WHERE Execucao.ID_Execucao = " + 
	          (String)idsExecucao.get(i));
	        this.dictionary.put("index", idsExecucao.get(i));
	        this.dictionary.put("Expected Result", expectedResult);
	        listReturn.add(new Object[] { this.dictionary.get("Test Case"), new DataDictionary(this.dictionary) });
	      }
	    }
	    database.closeConnection();
	    
	    Object[] objectReturn = new Object[listReturn.size()];
	    objectReturn = listReturn.toArray(objectReturn);
	    return objectReturn;
	}
}
