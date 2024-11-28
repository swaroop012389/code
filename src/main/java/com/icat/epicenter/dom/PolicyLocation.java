/** Program Description: Methods and queries built up for PolicyLocation table
 *  Author			   : SMNetserv
 *  Date of Creation   : 24/02/2018
**/

package com.icat.epicenter.dom;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;

public class PolicyLocation {
	public String policyNumber;
	public String transactionId;

	public String policyLocation;
	public String hvn;
	public String biValue;

	public Map<String, String> TCData;
	public Map<String, Object> results;
	private static List<Integer> testStatus;
	DBFrameworkConnection connection;
	QueryBuilder build;
	public Map<String, Object> policyLocationResults;

	public PolicyLocation(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("PolicyLocation");
		policyLocation = properties.getProperty("db_TableNamePolicyLocation");
		hvn = properties.getProperty("db_Hvn");
		biValue = properties.getProperty("db_BiValue");


		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(policyLocation);

	}

	public Map<String, Object> getPolicyLocationDetails(int transactionId, int i) {
		List<String> outFields = new ArrayList<>();

		outFields.add(hvn);
		outFields.add(biValue);

		build.outFields(outFields);
		build.whereBy("transactionId" + " =" + transactionId);
		List<Map<String, Object>> policyLocationDetails = build.execute(60);
		return results = policyLocationDetails.get(i);
	}

	public List<Map<String, String>> getPolicyLocationDataExpected(String tcid) {
		List<Map<String, String>> binderPolicyLocationData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"PolicyLocation").readExcelRowWise();

		//System.out.println("binderPolicyLocationData size = " + binderPolicyLocationData.size());
		//System.out.println("policyLocation tcid = " + tcid);

		ArrayList<Map<String, String>> TCData = new ArrayList<>();
		for (Map<String, String> element : binderPolicyLocationData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData.add(element);
			}
		}
		return TCData;

	}

	public String verifyPolicyLocationDetails(List<Map<String, String>> binderData, int transactionId) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		DecimalFormat df = new DecimalFormat("0");
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		for (int i = 0; i < binderData.size(); i++) {

			Map<String, String> binderDataRow = binderData.get(i);
			Map<String, Object> aftershockRow = getPolicyLocationDetails(transactionId, i);
			testStatus = Assertions.verify(aftershockRow.get("HVN").toString(), binderDataRow.get("HVN").toString(), "HVN : " + aftershockRow.get("HVN"), "Expected : " + binderDataRow.get("HVN").toString(), false, false);
			testStatus = Assertions.verify(aftershockRow.get("BIValue").toString().replace(".0", ""), binderDataRow.get("BIValue").toString(), "BIValue : " + aftershockRow.get("BIValue").toString().replace(".0", ""), "Expected : " + binderDataRow.get("BIValue").toString(), false, false);
		}

		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}

}
