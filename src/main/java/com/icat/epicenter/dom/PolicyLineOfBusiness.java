/** Program Description: Methods and queries built up for PolicyLineOfBusiness table
 *  Author			   : SMNetserv
 *  Date of Creation   : 24/02/2018
**/

package com.icat.epicenter.dom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.database.DatabaseConfiguration;
//import com.epicenter.POM.ExcelReport;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.FWProperties;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;
import com.icat.epicenter.utils.ExcelReportUtil;

public class PolicyLineOfBusiness {
	public String tableName;
	public String policyNumber;
	public String LOBId;
	public String policyTypeId;
	public String createDateTime;
	public String defVersionId;
	public String perilId;
	public FWProperties property;
	public Map<String, String> TCData;
	public Map<String, Object> results;
	private String createDate;
	DBFrameworkConnection connection;
	QueryBuilder build;
	private static List<Integer> testStatus;

	public PolicyLineOfBusiness(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("PolicyLineOfBusiness");
		tableName = properties.getProperty("db_TableName");
		policyNumber = properties.getProperty("db_PolicyNumber");
		LOBId = properties.getProperty("db_LOBID");
		policyTypeId = properties.getProperty("db_PolicyTypeId");
		createDateTime = properties.getProperty("db_CreateDateTime");
		defVersionId = properties.getProperty("db_DefVersionId");
		perilId = properties.getProperty("db_PerilId");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);

		build.tableName(tableName);
	}

	public Map<String, Object> getPolicyLineOfBusinessDetails(String policyNumber) {
		ArrayList<String> outFields = new ArrayList<>();
		outFields.add(LOBId);
		outFields.add(policyTypeId);
		outFields.add(createDateTime);
		outFields.add(defVersionId);
		outFields.add(policyTypeId);
		outFields.add(perilId);
		build.outFields(outFields);
		build.whereBy("policyNumber = '" + policyNumber +"'");
		List<Map<String, Object>> policyLineOfBusinessDetails = build.execute(60);
		return results = policyLineOfBusinessDetails.get(0);
	}

	public Map<String, String> getPolicyLineOfBusinessDataExpected(String tcid) {
		/*List<Map<String, String>> binderPolicyLineOfBusinessData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"PolicyLineOfBusiness").readExcelRowWise();*/

		List<Map<String, String>> binderPolicyLineOfBusinessData = new SheetMatchedAccessManager(
				EnvironmentDetails.getEnvironmentDetails().getString("PNBCommercialBinderTestFilePath"),
				"PolicyLineOfBusiness").readExcelRowWise();

		for (Map<String, String> element : binderPolicyLineOfBusinessData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData = element;
				break;
			}
		}
		return TCData;
	}

	public String verifyPolicyLineOfBusinessDetails(Map<String, String> binderData, List<Map<String, String>> testDataSetup, String policyNumber) {
		System.out.println("line 85 policyNumber = " + policyNumber);
		results = getPolicyLineOfBusinessDetails(policyNumber);

		testStatus = Assertions.verify(String.valueOf(results.get("LOBId")), binderData.get("LOBId"), "LOBId : " + results.get("LOBId"), "Expected : " + binderData.get("LOBId"), false, false);
		testStatus = Assertions.verify(String.valueOf(results.get("PolicyTypeId")), binderData.get("PolicyTypeId"), "PolicyTypeId : " + results.get("PolicyTypeId"),"Expected : " + binderData.get("PolicyTypeId"), false, false);

		//TF note - pre-existing data scenarios will have the date in the binder details, new business will be the day it was bound
		if (!binderData.get("CreateDateTime").equals("")) {
			createDate = binderData.get("CreateDateTime");
		} else {
			createDate = ExcelReportUtil.getInstance().getLatestBindRequestDateFromMaster(binderData.get("TCID"));
		}
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/YYYY");
		LocalDate tempDate = LocalDate.parse(String.valueOf(results.get("CreateDateTime")).replace(" 00:00:00.0", ""));
		String dbCreateDate = tempDate.format(f);
		testStatus = Assertions.verify(dbCreateDate, createDate, "CreateDateTime : " + dbCreateDate,"Expected : " + createDate, false, false);
		testStatus = Assertions.verify(String.valueOf(results.get("DefVersionId")), binderData.get("DefVersionId"), "DefVersionId : " + results.get("DefVersionId"),"Expected : " + binderData.get("DefVersionId"), false, false);
		testStatus = Assertions.verify(String.valueOf(results.get("PerilId")), binderData.get("PerilId"), "PerilId : " + results.get("PerilId"),"Expected : " + binderData.get("PerilId"), false, false);

		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}
}
