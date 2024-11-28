/** Program Description: Methods and queries built up for Location table
 *  Author			   : SMNetserv
 *  Date of Creation   : 17/02/2018
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

public class Location {
	public String policyNumber;
	public String transactionId;
	public String policyLocationId;
	public String locationNumber;

	public String location;
	public String hvn;
	public String description;
	public String product;
	public String policyInsuredId;
	public String createDateTime;
	public String deleteDateTime;
	public String deleteTransactionId;
	//public String cglOccupancyId;

	public Map<String, Object> results;
	private static List<Integer> testStatus;
	DBFrameworkConnection connection;
	QueryBuilder build;
	public Map<String, Object> locationResults;


	public Location(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("Location");
		location = properties.getProperty("db_TableNameLocation");
		transactionId = properties.getProperty("db_TransactionId");
		policyLocationId = properties.getProperty("db_PolicyLocationId");

		hvn = properties.getProperty("db_Hvn");
		locationNumber = properties.getProperty("db_LocationNumber");
		product = properties.getProperty("db_Product");
		policyInsuredId = properties.getProperty("db_PolicyInsuredId");
		description = properties.getProperty("db_Description");
		createDateTime = properties.getProperty("db_CreateDateTime");
		deleteDateTime = properties.getProperty("db_DeleteDateTime");
		deleteTransactionId = properties.getProperty("db_DeleteTransactionId");
		//cglOccupancyId = properties.getProperty("db_CGLOccupancyId");

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(location);
	}

	public Map<String, Object> getLocationDetails(int transactionID, int i) {
		List<String> outFields = new ArrayList<>();

		outFields.add(hvn);
		outFields.add(locationNumber);
		outFields.add(product);
		outFields.add(policyInsuredId);
		outFields.add(description);
		outFields.add(createDateTime);
		outFields.add(deleteDateTime);
		outFields.add(deleteTransactionId);
		//outFields.add(cglOccupancyId);

		build.outFields(outFields);
		build.whereBy("transactionId = " + transactionID + " order by " + createDateTime );
		List<Map<String, Object>> locationDetails = build.execute(60);
		//TODO - more than one row may be returned, get them all
		return results = locationDetails.get(i);
	}


	public List<Map<String, String>> getLocationDataExpected(String tcid) {
		List<Map<String, String>> binderLocationData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"Location").readExcelRowWise();

		ArrayList<Map<String, String>> TCData = new ArrayList<>();
		for (Map<String, String> element : binderLocationData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData.add(element);

			}
		}
		return TCData;

	}

	public String verifyLocationDetails(List<Map<String, String>> binderData, int transactionId) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		//System.out.println("binderData = " + binderData);
		//System.out.println("binderData size = " + binderData.size());

		for (int i = 0; i < binderData.size(); i++) {

			Map<String, String> binderDataRow = binderData.get(i);
			Map<String, Object> aftershockRow = getLocationDetails(transactionId, i);

			testStatus = Assertions.verify(aftershockRow.get("HVN").toString(), binderDataRow.get("HVN").toString(), "HVN : " + aftershockRow.get("HVN").toString(), "Expected : " + binderDataRow.get("HVN").toString(), false, false);
			testStatus = Assertions.verify(aftershockRow.get("LocationNumber").toString(), binderDataRow.get("LocNum").toString(), "LocNum : " + aftershockRow.get("LocationNumber").toString(), "Expected : " + binderDataRow.get("LocNum").toString(), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Product").toString(), binderDataRow.get("Product").toString(), "Product : " + aftershockRow.get("Product").toString(), "Expected : " + binderDataRow.get("Product").toString(), false, false);
			//need to get policyInsuredId from the policy table
			String expectedPolInsuredId = build.fetch("PolicyInsuredId", "Policy","TransactionId = " + transactionId).toString();
			testStatus = Assertions.verify(aftershockRow.get("PolicyInsuredId").toString(), expectedPolInsuredId, "PolicyInsuredId : " + aftershockRow.get("PolicyInsuredId"), "Expected : " + expectedPolInsuredId, false, false);
			testStatus = Assertions.verify(aftershockRow.get("Description").toString(), binderDataRow.get("Description").toString(), "Description : " + aftershockRow.get("Description").toString(), "Expected : " + binderDataRow.get("Description").toString(), false, false);
				String epiProcessDate = ExcelReportUtil.getInstance().getLatestProcessingDateFromMaster(binderDataRow.get("TCID"));
			LocalDate asCreateDateTime = LocalDate.parse(String.valueOf(aftershockRow.get("CreateDateTime")).substring(0,10));
			testStatus = Assertions.verify(asCreateDateTime.format(f), epiProcessDate, "CreateDateTime : " + asCreateDateTime.format(f), "Expected : " + epiProcessDate, false, false);

			String expectedDeleteDate;
			String expectedDeleteTxnId;
			if (binderDataRow.get("DeleteTransactionId").toString().equals("0")) {
				expectedDeleteDate = "01/01/1900";
				expectedDeleteTxnId = "0";
			} else {
				expectedDeleteDate = ExcelReportUtil.getInstance().getLatestTransactionDateFromMaster(binderDataRow.get("TCID"));
				expectedDeleteTxnId = ExcelReportUtil.getInstance().getLatestTransactionIdFromMaster(binderDataRow.get("TCID"));
			}
			LocalDate asDeleteDateTime = LocalDate.parse(String.valueOf(aftershockRow.get("DeleteDateTime")).substring(0,10));
			testStatus = Assertions.verify(asDeleteDateTime.format(f), expectedDeleteDate, "DeleteDateTime : " + asDeleteDateTime.format(f), "Expected : " + expectedDeleteDate, false, false);
			testStatus = Assertions.verify(aftershockRow.get("DeleteTransactionId").toString(), expectedDeleteTxnId.toString(), "DeleteTransactionId : " + aftershockRow.get("DeleteTransactionId"), "Expected : " +expectedDeleteTxnId.toString(), false, false);
			//we're not writing GL anymore
			//testStatus = Assertions.verify(aftershockRow.get("CGLOccupancyId").toString(), binderDataRow.get("CGLOccupancyId").toString(), "CGLOccupancyId : " + aftershockRow.get("CGLOccupancyId"), "Expected : " + binderDataRow.get("CGLOccupancyId").toString(), false, false);

		}

		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}


	public Integer getLocationNumber(Integer policyLocationIDData) {
		return ((Short) build.fetch(locationNumber, null, policyLocationId + " = " + policyLocationIDData)).intValue();
	}

	public Integer getPolicyLocationID(String locationNumberData, List<Integer> policyLocationIDData) {
		String policyLocationIDStr = "";
		for (int i = 0; i < policyLocationIDData.size() - 1; i++) {
			policyLocationIDStr += policyLocationIDData.get(i) + ",";
		}
		policyLocationIDStr += policyLocationIDData.get(policyLocationIDData.size() - 1);
		build.whereBy(locationNumber + " = " + locationNumberData + " and " + policyLocationId + " in ("
				+ policyLocationIDStr + ")");
		return (Integer) build.fetch(policyLocationId, null, null);
	}


}
