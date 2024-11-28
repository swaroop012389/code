/** Program Description: Methods and queries built up for LocationExtension table
 *  Author			   : SMNetserv
 *  Date of Creation   : 15/02/2018
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

public class LOCExtension {
	public String policyNumber;
	public String transactionId;
	public String policyLocationId;
	public String policyBuildingId;

	public String locExtension;
	public String hvn;
	public String formDescriptionId;
	public String limit;
	public String value;
	public String limitOccurrenceId;
	public String limitTypeId;
	public String attributeName;
	public String attributeValue;
	public String deleteTransactionId;

	Location loc;
	public Map<String, String> TCData;
	public Map<String, Object> results;
	private static List<Integer> testStatus;
	public DBFrameworkConnection connection;
	public QueryBuilder build;
	public Map<String, Object> locExtensionResults;



	public LOCExtension(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("LOCExtension");
		locExtension = properties.getProperty("db_TableNameLOCExtension");

		policyNumber = properties.getProperty("db_PolicyNumber");
		policyLocationId = properties.getProperty("db_PolicyLocationId");
		policyBuildingId = properties.getProperty("db_PolicyBuildingId");
		transactionId = properties.getProperty("db_TransactionId");

		hvn = properties.getProperty("db_Hvn");
		formDescriptionId = properties.getProperty("db_FormDescriptionId");
		limit = properties.getProperty("db_Limit");
		value = properties.getProperty("db_Value");
		limitOccurrenceId = properties.getProperty("db_LimitOccurrenceId");
		limitTypeId = properties.getProperty("db_LimitTypeId");
		attributeName = properties.getProperty("db_AttributeName");
		attributeValue = properties.getProperty("db_AttributeValue");
		deleteTransactionId = properties.getProperty("db_DeleteTransactionId");

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(locExtension);
		loc = new Location(dbConfig);

	}

	public Map<String, Object> getLocExtensionDetails(int transactionId, int i) {
		List<String> outFields = new ArrayList<>();
		outFields.add(hvn);
		outFields.add(formDescriptionId);
		outFields.add(limit);
		outFields.add(value);
		outFields.add(limitOccurrenceId);
		outFields.add(limitTypeId);
		outFields.add(attributeName);
		outFields.add(attributeValue);
		outFields.add(deleteTransactionId);

		build.outFields(outFields);
		build.whereBy("transactionId" + " = " + transactionId + " order by FormDescriptionId, AttributeName");
		List<Map<String, Object>> locExtensionDetails = build.execute(60);
		return results = locExtensionDetails.get(i);

	}

	public List<Map<String, String>> getLocExtensionDataExpected(String tcid) {
		List<Map<String, String>> binderLocExtensionData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"LocExtension").readExcelRowWise();

		ArrayList<Map<String, String>> TCData = new ArrayList<>();
		for (Map<String, String> element : binderLocExtensionData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData.add(element);
			}
		}
		return TCData;

	}

	public String verifyLocExtensionDetails(List<Map<String, String>> binderData, int transactionId) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		DecimalFormat df = new DecimalFormat("0");
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		for (int i = 0; i < binderData.size(); i++) {
			Map<String, String> binderDataRow = binderData.get(i);
			Map<String, Object> aftershockRow = getLocExtensionDetails(transactionId, i);

			testStatus = Assertions.verify(aftershockRow.get("HVN").toString(), binderDataRow.get("HVN").toString(), "HVN : " + aftershockRow.get("HVN").toString(), "Expected : " + binderDataRow.get("HVN").toString(), false, false);
			testStatus = Assertions.verify(aftershockRow.get("FormDescriptionId").toString(), binderDataRow.get("FormDescriptionId").toString(), "FormDescriptionId : " + aftershockRow.get("FormDescriptionId"), "Expected : " + binderDataRow.get("FormDescriptionId").toString(), false, false);
			String limitDBValue = "NULL";
			if (aftershockRow.get("Limit") != null){
				limitDBValue = aftershockRow.get("Limit").toString().replace(".00000", "").replace(".00", "");
			}
			testStatus = Assertions.verify(limitDBValue, binderDataRow.get("Limit").toString(), "Limit : " + limitDBValue, "Expected : " + binderDataRow.get("Limit").toString(), false, false);
			String valueDBValue = "NULL";
			if (aftershockRow.get("Value") != null){
				valueDBValue = aftershockRow.get("Value").toString().split("\\.")[0];
			}
			testStatus = Assertions.verify(valueDBValue, binderDataRow.get("Value").toString().split("\\.")[0], "Value : " + valueDBValue, "Expected : " + binderDataRow.get("Value").toString().split("\\.")[0], false, false);
			String limitOccurrenceIdDBValue = "NULL";
			if (aftershockRow.get("LimitOccurrenceId") != null){
				limitOccurrenceIdDBValue = aftershockRow.get("LimitOccurrenceId").toString();
			}
			testStatus = Assertions.verify(limitOccurrenceIdDBValue, binderDataRow.get("LimitOccurrenceId").toString(), "LimitOccurrenceId : " + limitOccurrenceIdDBValue, "Expected : " + binderDataRow.get("LimitOccurrenceId").toString(), false, false);
			String limitTypeIdDBValue = "NULL";
			if (aftershockRow.get("LimitTypeId") != null){
				limitTypeIdDBValue = aftershockRow.get("LimitTypeId").toString();
			}
			testStatus = Assertions.verify(limitTypeIdDBValue, binderDataRow.get("LimitTypeId").toString(), "LimitTypeId : " + limitTypeIdDBValue, "Expected : " + binderDataRow.get("LimitTypeId").toString(), false, false);
			String attributeNameDBValue = "NULL";
			if (aftershockRow.get("AttributeName") != null){
				attributeNameDBValue = aftershockRow.get("AttributeName").toString();
			}
			testStatus = Assertions.verify(attributeNameDBValue, binderDataRow.get("AttributeName").toString(), "AttributeName : " + attributeNameDBValue, "Expected : " + binderDataRow.get("AttributeName").toString(), false, false);
			String attributeValueDBValue = "NULL";
			String attributeValueExpected = "NULL";
			if (aftershockRow.get("AttributeValue") != null){
				//permit period dates aren't checked in the old framework - epicenter changes the entered dates, so just check month
				if (aftershockRow.get("AttributeName").toString().contains("Permit Period")) {
					attributeValueDBValue = aftershockRow.get("AttributeValue").toString().split(" ")[0];
					attributeValueExpected = binderDataRow.get("AttributeValue").toString().split(" ")[0];
				} else {
					attributeValueDBValue = aftershockRow.get("AttributeValue").toString();
					attributeValueExpected = binderDataRow.get("AttributeValue").toString();
				}
			}
			testStatus = Assertions.verify(attributeValueDBValue, attributeValueExpected, "AttributeValue : " + attributeValueDBValue, "Expected : " + attributeValueExpected, false, false);

			String expectedDeleteTxnId;
			if (binderDataRow.get("DeleteTransactionId").toString().equals("0")) {
				expectedDeleteTxnId = "0";
			} else {
				expectedDeleteTxnId = String.valueOf(transactionId);
			}

			testStatus = Assertions.verify(aftershockRow.get("DeleteTransactionId").toString(), expectedDeleteTxnId, "DeleteTransactionId : " + aftershockRow.get("DeleteTransactionId"), "Expected : " + expectedDeleteTxnId, false, false);


		}
		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}


}
