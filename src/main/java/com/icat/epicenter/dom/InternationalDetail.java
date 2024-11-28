/** Program Description: Methods and queries built up for InternationalDetail table
 *  Author			   : SMNetserv
 *  Date of Creation   : 18/02/2018
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

public class InternationalDetail {
	public String intlAdd1;
	public String intlAdd2;
	public String intlAdd3;
	public String country;
	public String zip;
	public String intlTable;
	public String intlID;
	public String intlTransactionID;
	public String hvn;
	DBFrameworkConnection connection;
	QueryBuilder build;
	public Map<String, Object> intlResults;
	public Map<String, Object> intlAIResults;
	private static List<Integer> testStatus;

	public InternationalDetail(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("InternationalDetail");
		intlAdd1 = properties.getProperty("db_IntlAddr1");
		intlAdd2 = properties.getProperty("db_IntlAddr2");
		intlAdd3 = properties.getProperty("db_IntlAddr3");
		country = properties.getProperty("db_Country");
		zip = properties.getProperty("db_ZIP");
		intlTable = properties.getProperty("db_IntlAddrTable");
		intlID = properties.getProperty("db_IntlAddrID");
		intlTransactionID = properties.getProperty("db_IntlTransactionId");
		hvn = properties.getProperty("db_Hvn");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(intlTable);
	}

	public Map<String, Object> getInternationalAddress(int id) {
		List<String> outfield = new ArrayList<>();
		outfield.add(intlAdd1);
		outfield.add(intlAdd2);
		outfield.add(country);
		build.outFields(outfield);
		build.whereBy(intlID + " = " + id);
		List<Map<String, Object>> Details = build.execute(60);
		return intlResults = Details.get(0);
	}

	public Map<String, Object> getInternationalAddressforPNB(int id, int transactionid) {
		List<String> outfield = new ArrayList<>();
		outfield.add(intlAdd1);
		outfield.add(intlAdd2);
		outfield.add(country);
		build.outFields(outfield);
		build.whereBy(intlID + " = " + id + " and " + intlTransactionID + " =" + transactionid);
		List<Map<String, Object>> Details = build.execute(60);
		return intlResults = Details.get(0);
	}

	public String verifyIntlDetails(Map<String, String> testData, int id) {
		intlResults = getInternationalAddress(id);
		Assertions.verify(intlResults.get("Address1"), testData.get("InsuredAddr1"),
				"International Address 1 : " + intlResults.get("Address1"),
				"International Address 1 : " + testData.get("InsuredAddr1"), false, false);
		Assertions.verify(intlResults.get("Address2"), testData.get("InsuredAddr2"),
				"International Address 2 : " + intlResults.get("Address2"),
				"International Address 2 : " + testData.get("InsuredAddr2"), false, false);
		testStatus=Assertions.verify(intlResults.get("Country"), testData.get("InsuredCountry"),
				"International Country : " + intlResults.get("Country"),
				"International Country : " + testData.get("InsuredCountry"), false, false);
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}

	}

	public void verifyIntlDetailsforPNB(List<Map<String, String>> pnbtestData, int id, int transactionNumber,
			int transactionid) {
		intlResults = getInternationalAddressforPNB(id, transactionid);
		boolean insuredAddress1 = false;
		boolean insuredCity = false;
		boolean insuredCountry = false;
		for (int p = transactionNumber; p >= 0; p--) {
			if (!pnbtestData.get(p).get("InsuredAddr1").equals("") && !insuredAddress1) {
				if (p == transactionNumber)
					Assertions.verify(intlResults.get("Address1"), pnbtestData.get(p).get("InsuredAddr1"),
							"International Address1 : " + intlResults.get("Address1"),
							"International Address1 : " + pnbtestData.get(p).get("InsuredAddr1"), false, false);
				else
					Assertions.addInfo("International Address1 : " + intlResults.get("Address1"),
							"International Address1 : " + pnbtestData.get(p).get("InsuredAddr1"));
				insuredAddress1 = true;
			}
			if (!pnbtestData.get(p).get("InsuredCity").equals("") && !insuredCity) {
				if (p == transactionNumber)
					Assertions.verify(intlResults.get("Address2"), pnbtestData.get(p).get("InsuredAddr2"),
							"International Address2 : " + intlResults.get("Address2"),
							"International Address2 : " + pnbtestData.get(p).get("InsuredAddr2"), false, false);
				else
					Assertions.addInfo("International Address2 : " + intlResults.get("Address2"),
							"International Address2 : " + pnbtestData.get(p).get("InsuredAddr2"));
				insuredCity = true;
			}
			if (!pnbtestData.get(p).get("InsuredCountry").equals("") && !insuredCountry) {
				if (p == transactionNumber)
					Assertions.verify(intlResults.get("Country"), pnbtestData.get(p).get("InsuredCountry"),
							"International Country : " + intlResults.get("Country"),
							"International Country : " + pnbtestData.get(p).get("InsuredCountry"), false, false);
				else
					Assertions.addInfo("International Country : " + intlResults.get("Country"),
							"International Country : " + pnbtestData.get(p).get("Country"));
				insuredCountry = true;
			}
		}
	}

	public Map<String, Object> getAdditionalInterestInternationalAddress(int id) {
		List<String> outfield = new ArrayList<>();
		outfield.add(intlAdd1);
		outfield.add(intlAdd2);
		build.outFields(outfield);
		build.whereBy(intlTransactionID + " = " + id);
		List<Map<String, Object>> Details = build.execute(60);
		return intlAIResults = Details.get(0);
	}

	public void verifyAdditionalInterestIntlDetails(Map<String, String> testData, int id, int aiNumber) {
		intlAIResults = getAdditionalInterestInternationalAddress(id);
		Assertions.verify(intlAIResults.get("Address1"), testData.get(aiNumber + "-AIAddr1"),
				"International Address 1 : " + intlAIResults.get("Address1"),
				"International Address 1 : " + testData.get(aiNumber + "-AIAddr1"), false, false);
		Assertions.verify(intlAIResults.get("Address2"), testData.get(aiNumber + "-AIAddr2"),
				"International Address 2 : " + intlAIResults.get("Address2"),
				"International Address 2 : " + testData.get(aiNumber + "-AIAddr2"), false, false);
	}

	public void verifyAdditionalInterestIntlDetailsAdditionalInfo(Map<String, String> testData, int id,
			int aiNumber) {
		intlAIResults = getAdditionalInterestInternationalAddress(id);
		Assertions.addInfo("International Address 1 : " + intlAIResults.get("Address1"),
				"International Address 1 : " + testData.get(aiNumber + "-AIAddr1"));
		Assertions.addInfo("International Address 2 : " + intlAIResults.get("Address2"),
				"International Address 2 : " + testData.get(aiNumber + "-AIAddr2"));
	}
}
