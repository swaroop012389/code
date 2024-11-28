/** Program Description: Methods and queries built up for SpecialCLass table
 *  Author			   : SMNetserv
 *  Date of Creation   : 12/02/2018
**/

package com.icat.epicenter.dom;

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

public class SpecialClass {
	static String ext_rpt_msg = " is verified.";
	public String specialClassTable;
	public String hvn;
	public String policyLocationId;
	public String specialClassNameId;
	public String specialClassName;
	public String bldValue;
	public String deleteTransactionId;

	/* RESIDENTIAL PARAMETERS
	public String buildingValue;
	public String policySpecialClassId;
		Location location;
	PolicyLocation policyLocation;
	 */

	public String transactionId;
	public DBFrameworkConnection connection;
	public QueryBuilder build;
	public Map<String, Object> results;

	private static List<Integer> testStatus;

	public SpecialClass(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("SpecialClass");
		specialClassTable = properties.getProperty("db_SpecialClassTable");
		hvn = properties.getProperty("db_Hvn");
		policyLocationId = properties.getProperty("db_PolicyLocationId");
		specialClassNameId = properties.getProperty("db_SpecialClassNameId");
		specialClassName = properties.getProperty("db_SpecialClassName");
		bldValue = properties.getProperty("db_BldValue");
		deleteTransactionId = properties.getProperty("db_DeleteTransactionId");

		/* RESIDENTIAL PARAMETERS
		buildingValue = properties.getProperty("db_BuildingValue");
		policySpecialClassId = properties.getProperty("db_PolicySpecialClassId");
		location = new Location(dbConfig);
		policyLocation = new PolicyLocation(dbConfig);

		 */

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(specialClassTable);

	}

	public Map<String, Object> getSpecialClassDetails(int transactionId, int i) {
		List<String> outFields = new ArrayList<>();
		outFields.add(hvn);
		outFields.add(policyLocationId);
		outFields.add(specialClassNameId);
		outFields.add(specialClassName);
		outFields.add(bldValue);
		outFields.add(deleteTransactionId);

		build.outFields(outFields);
		build.whereBy("transactionId = " + transactionId + " order by PolicyLocationId, SpecialClassNameId, SpecialClassName");
		List<Map<String, Object>> specialClassDetails = build.execute(60);
		return specialClassDetails.get(i);
	}

	public List<Map<String, String>> getSpecialClassDataExpected(String tcid) {
		List<Map<String, String>> specialClassData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"SpecialClass").readExcelRowWise();

		ArrayList<Map<String, String>> TCData = new ArrayList<>();
		for (Map<String, String> element : specialClassData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
								TCData.add(element);
			}
		}

		return TCData;

	}

	public String verifySpecialClassData(List<Map<String, String>> binderData, int transactionId) {

		for (int i = 0; i < binderData.size(); i++) {
			Map<String, String> binderDataRow = binderData.get(i);
			Map<String, Object> aftershockRow = getSpecialClassDetails(transactionId, i);

			testStatus = Assertions.verify(aftershockRow.get("HVN").toString(), binderDataRow.get("HVN"), "HVN : " + aftershockRow.get("HVN").toString(), "Expected : " + binderDataRow.get("HVN"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("SpecialClassNameId").toString(), binderDataRow.get("SpecialClassNameId"), "SpecialClassNameId : " + aftershockRow.get("SpecialClassNameId").toString(), "Expected : " + binderDataRow.get("SpecialClassNameId"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("SpecialClassName").toString(), binderDataRow.get("SpecialClassName"), "SpecialClassName : " + aftershockRow.get("SpecialClassName"), "Expected : " + binderDataRow.get("SpecialClassName"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("BldValue").toString().replace(".0", ""), binderDataRow.get("BldValue"), "BldValue : " + aftershockRow.get("BldValue").toString().replace(".0", ""), "Expected : " + binderDataRow.get("BldValue"), false, false);
			String expectedDeleteTxnId;
			if (binderDataRow.get("DeleteTransactionId").toString().equals("0")) {
				expectedDeleteTxnId = "0";
			} else {
				expectedDeleteTxnId = ExcelReportUtil.getInstance().getLatestTransactionIdFromMaster(binderDataRow.get("TCID"));
			}
			testStatus = Assertions.verify(aftershockRow.get("DeleteTransactionId").toString(), expectedDeleteTxnId.toString(), "DeleteTransactionId : " + aftershockRow.get("DeleteTransactionId"), "Expected : " +expectedDeleteTxnId.toString(), false, false);

		}

		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}





	/* RESIDENTIAL METHODS
	public Map<String, Object> getBldgandSpecialClass(Integer policySpecialClassid) {
		List<String> outFields = new ArrayList<>();
		outFields.add(buildingValue);
		outFields.add(specialClassName);
		build.outFields(outFields);
		build.whereBy(policySpecialClassId + " = " + policySpecialClassid);
		List<Map<String, Object>> bldgAndSpecialClass = build.execute(60);
		return bldgAndSpecialClass.get(0);
	}

	public Integer getPolicySpecialClassID(String SpecialClassNameArg, Integer policyLocationIDNum) {
		return (Integer) build.fetch(policySpecialClassId, null, specialClassName + " = '" + SpecialClassNameArg
				+ "' and " + policyLocationId + " = " + policyLocationIDNum);
	}

	public String verifybuildingandSpecialDetails(Map<String, String> binderData, Integer specialClassid) {
		Assertions.addInfo("<span class='group'> L" + binderData.get("LocNum") + " : "
				+ binderData.get("SpecialClassName") + "</span>", "<span class='group'> GROUPING </span>");
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		Map<String, Object> dbData = getBldgandSpecialClass(specialClassid);
		Assertions.verify(dbData.get(specialClassName), binderData.get("SpecialClassName"),
				"Special Class Name : " + getBldgandSpecialClass(specialClassid).get(specialClassName),
				"Special Class Name : " + binderData.get("SpecialClassName"), false, false);
		testStatus=Assertions.verify(String.valueOf((int) (double) dbData.get(buildingValue)), binderData.get("APCCoverage"),
				"APC Coverage Limit : "
						+ format.format((int) (double) getBldgandSpecialClass(specialClassid).get(buildingValue)),
				"APC Coverage Limit : " + format.format(Integer.valueOf(binderData.get("APCCoverage"))), false, false);
		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}

	}

	public String getSpecialClassName(Integer policySpclClassID) {
		return build.fetch(specialClassName, null, policySpecialClassId + "=" + policySpclClassID).toString();
	}

	public String getTotalAPCCoverage(Integer policyLocatioIdNum) {
		List<String> outFields = new ArrayList<>();
		outFields.add("sum(" + buildingValue + ") as TotalBuildingPremium");
		build.outFields(outFields);
		build.whereBy(policyLocationId + " = " + policyLocatioIdNum);
		build.groupBy(policyLocationId);
		List<Map<String, Object>> sumdetails = build.execute(60);
		return (int) (double) sumdetails.get(0).get("TotalBuildingPremium") + "";
	}

	public String verifyTotalCoverage(Integer policyLocationId, String APCCoverageTotal) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		testStatus=Assertions.verify(getTotalAPCCoverage(policyLocationId), APCCoverageTotal,
				"APCCoverageTotal : "
						+ format.format(Integer.parseInt(getTotalAPCCoverage(policyLocationId).toString())),
				"APCCoverageTotal : " + format.format(Integer.valueOf(APCCoverageTotal)), false, false);
		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	} */
}
