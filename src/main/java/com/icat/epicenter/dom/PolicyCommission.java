/** Program Description: Methods and queries built up for PolicyComission table
 *  Author			   : SMNetserv
 *  Date of Creation   :  23/02/2018
 **/

package com.icat.epicenter.dom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;
import com.icat.epicenter.utils.ExcelReportUtil;

public class PolicyCommission {
	public String policyNumber;
	public String transactionID;

	public String policyCommission;
	public String hvn;
	public String commissionPercent;
	public String icatRoleDistribution;
	public String createDateTime;

	public Map<String, String> TCData;
	public Map<String, Object> results;
	private static List<Integer> testStatus;
	DBFrameworkConnection connection;
	QueryBuilder build;
	public Map<String, Object> policyCommissionResults;

	public PolicyCommission(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("PolicyCommission");
		policyCommission = properties.getProperty("db_TableNamePolicyCommission");
		policyNumber = properties.getProperty("db_PolicyNumber");
		hvn = properties.getProperty("db_Hvn");
		commissionPercent = properties.getProperty("db_CommissionPercent");
		icatRoleDistribution = properties.getProperty("db_IcatRoleDistribution");
		createDateTime = properties.getProperty("db_CreateDateTime");

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(policyCommission);
	}

	public Map<String, Object> getPolicyCommissionDetails(String policyNumber, int transactionID) {

		List<String> outFields = new ArrayList<>();
		outFields.add(hvn);
		outFields.add(commissionPercent);
		outFields.add(icatRoleDistribution);
		outFields.add(createDateTime);

		build.outFields(outFields);
		build.whereBy("policyNumber = " + "'" + policyNumber + "'" + " and transactionId" + " =" + transactionID);
		List<Map<String, Object>> policyCommissionDetails = build.execute(60);
		return results = policyCommissionDetails.get(0);
	}

	public Map<String, String> getPolicyCommissionDataExpected(String tcid) {
		List<Map<String, String>> binderPolicyCommissionData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"PolicyCommission").readExcelRowWise();

		for (Map<String, String> element : binderPolicyCommissionData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData = element;
				break;
			}
		}
		return TCData;

	}

	public String verifyPolicyCommissionDetails(Map<String, String> binderData, String policyNumber, int txnId) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		//ExcelReport excelReport = new ExcelReport();

		//get the aftershock values
		policyCommissionResults = getPolicyCommissionDetails(policyNumber, txnId);
		testStatus = Assertions.verify(policyCommissionResults.get("HVN").toString(), binderData.get("HVN").toString(), "HVN : " + policyCommissionResults.get("HVN"), "Expected : " + binderData.get("HVN").toString(), false, false);
		testStatus = Assertions.verify(policyCommissionResults.get("CommissionPercent").toString(), binderData.get("CommissionPercent").toString(), "CommissionPercent : " + policyCommissionResults.get("CommissionPercent").toString(), "Expected : " + binderData.get("CommissionPercent").toString(), false, false);
		testStatus = Assertions.verify(policyCommissionResults.get("ICATRoleDistribution").toString(), binderData.get("ICATRoleDistribution").toString(), "ICATRoleDistribution : " + policyCommissionResults.get("ICATRoleDistribution").toString(), "Expected : " + binderData.get("ICATRoleDistribution").toString(), false, false);
		//using epicenter processing date
		String epiProcessDate = ExcelReportUtil.getInstance().getLatestProcessingDateFromMaster(binderData.get("TCID"));
		LocalDate asCreateDateTime = LocalDate.parse(String.valueOf(policyCommissionResults.get("CreateDateTime")).substring(0,10));
		testStatus = Assertions.verify(asCreateDateTime.format(f), epiProcessDate, "CreateDateTime : " + asCreateDateTime.format(f), "Expected : " + epiProcessDate, false, false);


		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}


}