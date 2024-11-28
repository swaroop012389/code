/** Program Description: Methods and queries built up for Insured table
 *  Author			   : SMNetserv
 *  Date of Creation   : 18/02/2018
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

public class AddressIntl {
	public String policyNumber;
	public String transactionId;

	public String addressIntl;
	public String hvn;
	public String addressIntlId;
	public String addressTypeId;
	public String address1;
	public String address2;
	public String address3;
	public String address4;
	public String country;

	public Map<String, String> TCData;
	public Map<String, Object> results;
	private static List<Integer> testStatus;
	DBFrameworkConnection connection;
	QueryBuilder build;
	public Map<String, Object> addressIntlResults;


	public AddressIntl(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("AddressIntl");
		addressIntl = properties.getProperty("db_AddressIntlTable");
		hvn = properties.getProperty("db_HVN");
		addressIntlId = properties.getProperty("db_AddressIntlId");
		addressTypeId = properties.getProperty("db_AddressTypeId");
		address1 = properties.getProperty("db_Address1");
		address2 = properties.getProperty("db_Address2");
		address3 = properties.getProperty("db_Address3");
		address4 = properties.getProperty("db_Address4");
		country= properties.getProperty("db_Country");

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(addressIntl);
	}

	public Map<String, Object> getAddressIntlDetails(String policyNumber, int transactionId) {
		List<String> outFields = new ArrayList<>();
		outFields.add(addressTypeId);
		outFields.add(address1);
		outFields.add(address2);
		outFields.add(address3);
		outFields.add(address4);
		outFields.add(country);

		build.outFields(outFields);
		build.whereBy("transactionId" + " =" + transactionId);
		List<Map<String, Object>> addressIntlDetails = build.execute(60);
		return results = addressIntlDetails.get(0);
	}

	public Map<String, String> getAddressIntlDataExpected(String tcid) {
		List<Map<String, String>> binderAddressIntlData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"AddressIntl").readExcelRowWise();

		//should be only one result, if any
		if (binderAddressIntlData.size() != 0) {
			if (binderAddressIntlData.get(0).get("TCID").equalsIgnoreCase(tcid)) {
				TCData = binderAddressIntlData.get(0);
			}
		} else {
			//clear any results from prior transactions
			TCData = null;
		}
		return TCData;

	}


	public String verifyAddressIntlDetails(Map<String, String> binderData, String policyNumber, int transactionId) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		DecimalFormat df = new DecimalFormat("0");
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		//get the aftershock values
		addressIntlResults = getAddressIntlDetails(policyNumber, transactionId);

		testStatus = Assertions.verify(addressIntlResults.get("AddressTypeId").toString(), binderData.get("AddressTypeId").toString(), "AddressTypeId : " + addressIntlResults.get("AddressTypeId").toString(), "Expected : " + binderData.get("AddressTypeId").toString(), false, false);
		testStatus = Assertions.verify(addressIntlResults.get("Address1"), binderData.get("Address1"), "Address1 : " + addressIntlResults.get("Address1"), "Expected : " + binderData.get("Address1"), false, false);
		testStatus = Assertions.verify(addressIntlResults.get("Address2").toString().trim(), binderData.get("Address2").toString().trim(), "Address2 : " + addressIntlResults.get("Address2").toString().trim(), "Expected : " + binderData.get("Address2").toString().trim(), false, false);
		if (!binderData.get("Address3").equalsIgnoreCase("null")) {
			testStatus = Assertions.verify(addressIntlResults.get("Address3"), binderData.get("Address3"), "Address3 : " + addressIntlResults.get("Address3"), "Expected : " + binderData.get("Address3"), false, false);
		}
		if (!binderData.get("Address4").equalsIgnoreCase("null")) {
			testStatus = Assertions.verify(addressIntlResults.get("Address4").toString().trim(), binderData.get("Address4").toString().trim(), "Address4 : " + addressIntlResults.get("Address4").toString().trim(), "Expected : " + binderData.get("Address4").toString().trim(), false, false);
		}
		testStatus = Assertions.verify(addressIntlResults.get("Country"), binderData.get("Country"), "Country : " + addressIntlResults.get("Country"), "Expected : " + binderData.get("Country"), false, false);



		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}





}
