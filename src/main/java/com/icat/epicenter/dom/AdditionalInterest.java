/** Program Description: Methods and queries built up for AdditionalInterest table
 *  Author			   : SMNetserv
 *  Date of Creation   : 17/02/2018
 **/

package com.icat.epicenter.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.FWProperties;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;

public class AdditionalInterest {
	public String additionalInterestTable;

	public String hvn;
	public String product;
	public String name;
	public String address1;
	public String address2;
	public String city;
	public String state;
	public String zip;
	public String loanNumber;
	public String deleteTransactionId;


	/* RESIDENTIAL?
	public String aIName;
	public String policyInsuredId;
	public String aITypeID;
	public String polAIID;
	public String country;
	public String mortgageeOrder;
		InternationalDetail internalidetails;*/


	public Map<String, Object> aIDBData;
	public DBFrameworkConnection connection;
	Map<String, String> dataTestSetup;
	SheetMatchedAccessManager dataTest;
	List<Map<String, String>> data;
	static String testDataPath;
	String testDataSetupSheetname;
	FWProperties property;
	QueryBuilder build;

	private static List<Integer> testStatus;

	public AdditionalInterest(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("AdditionalInterest");
		additionalInterestTable = properties.getProperty("db_TableNameAdditionalInterest");
		hvn = properties.getProperty("db_Hvn");
		product = properties.getProperty("db_Product");
		name = properties.getProperty("db_Name");
		address1 = properties.getProperty("db_Address1");
		address2 = properties.getProperty("db_Address2");
		city = properties.getProperty("db_City");
		state = properties.getProperty("db_State");
		zip = properties.getProperty("db_Zip");
		loanNumber = properties.getProperty("db_LoanNumber");
		deleteTransactionId = properties.getProperty("db_DeleteTransactionId");

		name = properties.getProperty("db_Name");


		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(additionalInterestTable);

		/* RESIDENTIAL?
		internalidetails = new InternationalDetail(dbConfig);
		policyInsuredId = properties.getProperty("db_PolicyInsuredID");
		aITypeID = properties.getProperty("db_AITypeID");
		polAIID = properties.getProperty("db_PolAdditionalInterestId");
		country = properties.getProperty("db_AICountry");
		mortgageeOrder = properties.getProperty("db_MortgaggeOrder"); */
	}


	public Map<String, Object> getAdditionalInterestDetails(int transactionId, int i) {

		List<String> outFields = new ArrayList<>();
		outFields.add(hvn);
		outFields.add(product);
		outFields.add(name);
		outFields.add(address1);
		outFields.add(address2);
		outFields.add(city);
		outFields.add(state);
		outFields.add(zip);
		outFields.add(loanNumber);
		outFields.add(deleteTransactionId);


		build.outFields(outFields);
		build.whereBy("transactionId = '" + transactionId + "'");
		List<Map<String, Object>> aiData = build.execute(60);
		return aIDBData = aiData.get(i);
	}

	public List<Map<String, String>> getAdditionalInterestDataExpected(String tcid) {
		List<Map<String, String>> additionalInterestData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"AdditionalInterest").readExcelRowWise();

		System.out.println("additionalInterestData.size() = " + additionalInterestData.size());

		ArrayList<Map<String, String>> TCData = new ArrayList<>();
		for (int i = 0; i < additionalInterestData.size(); i++) {
			if (additionalInterestData.get(i).get("TCID").equalsIgnoreCase(tcid)) {
				System.out.println("i = " + i);
				System.out.println("binderBuildingCoverageData.get(i) = " + additionalInterestData.get(i));
				TCData.add(additionalInterestData.get(i));
			}
		}

		return TCData;

	}

	public String verifyAdditionalInterestData(List<Map<String, String>> binderData, int transactionId) {
		for (int i = 0; i < binderData.size(); i++) {

			Map<String, String> binderDataRow = binderData.get(i);
			Map<String, Object> aftershockRow = getAdditionalInterestDetails(transactionId, i);

			//System.out.println("binderDataRow = " + binderDataRow);
			//System.out.println("aftershockRow = " + aftershockRow);

			testStatus = Assertions.verify(aftershockRow.get("HVN").toString(), binderDataRow.get("HVN"), "HVN : " + aftershockRow.get("HVN").toString(), "Expected : " + binderDataRow.get("HVN"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Product").toString(), binderDataRow.get("Product"), "Product : " + aftershockRow.get("Product"), "Expected : " + binderDataRow.get("Product"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Name"), binderDataRow.get("Name"), "Name : " + aftershockRow.get("Name"), "Expected : " + binderDataRow.get("Name"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Address1"), binderDataRow.get("Address1"), "Address1 : " + aftershockRow.get("Address1"), "Expected : " + binderDataRow.get("Address1"), false, false);
			testStatus = Assertions.verify(String.valueOf(aftershockRow.get("Address2")).trim(), String.valueOf(binderDataRow.get("Address2")).trim(), "Address2 : " + String.valueOf(aftershockRow.get("Address2")).trim(), "Expected : " + String.valueOf(binderDataRow.get("Address2")).trim(), false, false);
			testStatus = Assertions.verify(aftershockRow.get("City"), binderDataRow.get("City"), "City : " + aftershockRow.get("City"), "Expected : " + binderDataRow.get("City"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("State"), binderDataRow.get("State"), "State : " + aftershockRow.get("State"), "Expected : " + binderDataRow.get("State"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Zip"), binderDataRow.get("Zip"), "Zip : " + aftershockRow.get("Zip"), "Expected : " + binderDataRow.get("Zip"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("LoanNumber").toString().trim(), binderDataRow.get("LoanNumber").toString().trim(), "LoanNumber : " + aftershockRow.get("LoanNumber").toString().trim(), "Expected : " + binderDataRow.get("LoanNumber").toString().trim(), false, false);

			if (binderDataRow.get("DeleteTransactionId").toString().equals("0")) {
				testStatus = Assertions.verify(aftershockRow.get("DeleteTransactionId").toString(), binderDataRow.get("DeleteTransactionId"), "DeleteTransactionId : " + aftershockRow.get("DeleteTransactionId"), "Expected : " + binderDataRow.get("DeleteTransactionId"), false, false);
			} else {
				testStatus = Assertions.verify(aftershockRow.get("DeleteTransactionId").toString(), String.valueOf(transactionId), "DeleteTransactionId : " + aftershockRow.get("DeleteTransactionId"), "Expected : " + transactionId, false, false);
			}

		}
		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}

	}








	/* RESIDENTIAL METHODS
	public List<String> getaiTypeID(List<Integer> policyAdditionaInterestID) {
		String aiIDStr = "";
		for (int i = 0; i < policyAdditionaInterestID.size() - 1; i++) {
			aiIDStr += policyAdditionaInterestID.get(i) + ",";
		}
		aiIDStr += policyAdditionaInterestID.get(policyAdditionaInterestID.size() - 1);
		List<String> outFields = new ArrayList<>();
		outFields.add(aITypeID);
		build.outFields(outFields);
		build.whereBy(polAIID + " in (" + aiIDStr + ")");
		List<Map<String, Object>> aitypeid = build.execute(60);
		List<String> ids = new ArrayList<>();
		for (int i = 0; i < aitypeid.size(); i++) {
			ids.add((String) aitypeid.get(i).get(aITypeID));
		}
		return ids;
	}

		public Map<String, Object> getAdditionalInterestDetailsPNB(List<Integer> poladditionalIntersetID,
			BigDecimal typeid, String Name, int hvnNumber) {
		String aiIDStr = "";
		for (int i = 0; i < poladditionalIntersetID.size() - 1; i++) {
			aiIDStr += poladditionalIntersetID.get(i) + ",";
		}
		aiIDStr += poladditionalIntersetID.get(poladditionalIntersetID.size() - 1);
		List<String> outFields = new ArrayList<>();
		outFields.add(aIName);
		outFields.add(aIAddress1);
		outFields.add(aIAddress2);
		outFields.add(aICity);
		outFields.add(aIState);
		outFields.add(aIZip);
		outFields.add(country);
		outFields.add(mortgageeOrder);
		outFields.add(deleteTransactionId);
		outFields.add(hvn);
		build.outFields(outFields);
		build.whereBy(name + " = '" + Name + "' and " + aITypeID + " = '" + typeid + "' and " + hvn + " = " + hvnNumber
				+ " and " + polAIID + " in (" + aiIDStr + ")");
		List<Map<String, Object>> aiData = build.execute(60);
		if (aiData.isEmpty())
			return null;
		else
			return aIDBData = aiData.get(0);
	}

		public void verifyAdditionalInterestPNB(Map<String, String> testData, String aiType,
			List<Integer> poladditionalIntersetID, int i, BigDecimal typeid, int transactionid,
			String applicability, int hvnNumber) {
		aIDBData = getAdditionalInterestDetailsPNB(poladditionalIntersetID, typeid, testData.get(i + "-AIName"),
				hvnNumber);
		if (aIDBData == null) {
		} else {
			if (aIDBData.get("deleteTransactionid").equals(0)) {
				Assertions.addInfo(
						"<span class='group'> " + " Additional Interest " + testData.get(i + "-AIType") + "</span>",
						"");
				Assertions.verify(aIDBData.get("AIName"), testData.get(i + "-AIName"),
						"AI Name : " + aIDBData.get("AIName"), "AI Name : " + testData.get(i + "-AIName"), false,
						false);
				Assertions.verify(aiType, testData.get(i + "-AIType"), "AI Type : " + aiType,
						"AI Type : " + testData.get(i + "-AIType"), false, false);
				if (testData.get(i + "-AICountry").equalsIgnoreCase("USA")) {
					Assertions.verify(aIDBData.get("Address1"), testData.get(i + "-AIAddr1"),
							"AI Addr1 : " + aIDBData.get("Address1"), "AI Addr1 : " + testData.get(i + "-AIAddr1"),
							false, false);
					if (!testData.get(i + "-AIAddr2").equals(""))
						Assertions.verify(aIDBData.get("Address2").toString().trim(),
								testData.get(i + "-AIAddr2").trim(), "AI Addr2 : " + aIDBData.get("Address2"),
								"AI Addr2 : " + testData.get(i + "-AIAddr2"), false, false);
					Assertions.verify(aIDBData.get("City"), testData.get(i + "-AICity"),
							"AI City : " + aIDBData.get("City"), "AI City : " + testData.get(i + "-AICity"), false,
							false);
					Assertions.verify(aIDBData.get("State"), testData.get(i + "-AIState"),
							"AI State : " + aIDBData.get("State"), "AI State : " + testData.get(i + "-AIState"), false,
							false);
					Assertions.verify(aIDBData.get("Zip"), testData.get(i + "-AIZIP"),
							"AI ZIP : " + aIDBData.get("Zip"), "AI ZIP : " + testData.get(i + "-AIZIP"), false, false);
				} else {
					if (testData.get(i + "-AICountry").equalsIgnoreCase("Canada")
							|| testData.get(i + "-AICountry").equalsIgnoreCase("India")) {
						internalidetails.verifyAdditionalInterestIntlDetails(testData, transactionid, i);
					}
				}
				if (testData.get(i + "-AIType").equalsIgnoreCase("Mortgagee")) {
					if (aIDBData.get("MortgageeOrder").equals(1)) {
						Assertions.passTest("AI Mortgagee Order : " + "First Mortgagee",
								"AI Mortgagee Order : " + "First Mortgagee");
					}
					if (aIDBData.get("MortgageeOrder").equals(2)) {
						Assertions.passTest("AI Mortgagee Order : " + "Second Mortgagee",
								"AI Mortgagee Order : " + "Second Mortgagee");
					}
				}
			} else {
				Assertions.passTest(testData.get(i + "-AIType") + " " + applicability,
						"Deleted AI Transaction id : " + aIDBData.get("deleteTransactionid"));
			}
		}
	}

	public void verifyAdditionalInterestPNBAdditionalInfo(Map<String, String> testData, String aiType,
			List<Integer> poladditionalIntersetID, int i, BigDecimal typeid, int transactionid,
			String applicability, int hvnNumber) {
		aIDBData = getAdditionalInterestDetailsPNB(poladditionalIntersetID, typeid, testData.get(i + "-AIName"),
				hvnNumber);
		if (aIDBData == null) {
		} else {
			if (aIDBData.get("deleteTransactionid").equals(0)) {
				Assertions.addInfo(
						"<span class='group'> " + " Additional Interest " + testData.get(i + "-AIType") + "</span>",
						"");
				Assertions.addInfo("AI Name : " + aIDBData.get("AIName"), "AI Name : " + testData.get(i + "-AIName"));
				Assertions.addInfo("AI Type : " + aiType, "AI Type : " + testData.get(i + "-AIType"));
				if (testData.get(i + "-AICountry").equalsIgnoreCase("USA")) {
					Assertions.addInfo("AI Addr1 : " + aIDBData.get("Address1"),
							"AI Addr1 : " + testData.get(i + "-AIAddr1"));
					if (!testData.get(i + "-AIAddr2").equals(""))
						Assertions.addInfo("AI Addr2 : " + aIDBData.get("Address2"),
								"AI Addr2 : " + testData.get(i + "-AIAddr2"));
					Assertions.addInfo("AI City : " + aIDBData.get("City"), "AI City : " + testData.get(i + "-AICity"));
					Assertions.addInfo("AI State : " + aIDBData.get("State"),
							"AI State : " + testData.get(i + "-AIState"));
					Assertions.addInfo("AI ZIP : " + aIDBData.get("Zip"), "AI ZIP : " + testData.get(i + "-AIZIP"));
				} else {
					internalidetails.verifyAdditionalInterestIntlDetailsAdditionalInfo(testData, transactionid, i);
				}
				if (testData.get(i + "-AIType").equalsIgnoreCase("Mortgagee")) {
					if (aIDBData.get("MortgageeOrder").equals(1)) {
						Assertions.addInfo("AI Mortgagee Order : " + "First Mortgagee",
								"AI Mortgagee Order : " + "First Mortgagee");
					}
					if (aIDBData.get("MortgageeOrder").equals(2)) {
						Assertions.addInfo("AI Mortgagee Order : " + "Second Mortgagee",
								"AI Mortgagee Order : " + "Second Mortgagee");
					}
				}
			} else {
				Assertions.addInfo(testData.get(i + "-AIType") + " " + applicability,
						"Deleted AI Transaction id : " + aIDBData.get("deleteTransactionid"));
			}
		}
	}




	 */

}
