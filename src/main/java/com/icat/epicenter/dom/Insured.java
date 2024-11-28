/** Program Description: Methods and queries built up for Insured table
 *  Author			   : SMNetserv
 *  Date of Creation   : 18/02/2018
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

public class Insured {
	public String policyNumber;
	public String transactionId;

	public String insured;
	public String policyInsuredId;
	public String hvn;
	public String product;
	public String agentId;
	public String name;
	public String address1;
	public String address2;
	public String city;
	public String state;
	public String zip;
	public String country;
	public String prepared;
	public String agencyContact;
	public String renewalStatus;
	public String createDateTime;
	public String noMultipleApplications;
	public String exclusiveApplication;
	public String extendedNamedInsured;
	public String yearIncepted;
	public String formType;

	public Map<String, String> TCData;
	public Map<String, Object> results;
	private static List<Integer> testStatus;
	DBFrameworkConnection connection;
	QueryBuilder build;
	QueryBuilder polBuild;
	public Map<String, Object> insuredResults;


	public Insured(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("Insured");
		policyNumber = properties.getProperty("db_PolicyNumber");
		insured = properties.getProperty("db_TableNameInsured");
		policyInsuredId = properties.getProperty("db_PolicyInsuredId");
		hvn = properties.getProperty("db_HVN");
		product = properties.getProperty("db_Product");
		agentId = properties.getProperty("db_AgentId");
		name = properties.getProperty("db_Name");
		address1 = properties.getProperty("db_Address1");
		address2 = properties.getProperty("db_Address2");
		city = properties.getProperty("db_City");
		state = properties.getProperty("db_State");
		zip = properties.getProperty("db_Zip");
		country= properties.getProperty("db_Country");
		prepared= properties.getProperty("db_Prepared");
		agencyContact = properties.getProperty("db_AgencyContact");
		renewalStatus = properties.getProperty("db_RenewalStatus");
		createDateTime = properties.getProperty("db_CreateDateTime");
		noMultipleApplications = properties.getProperty("db_NoMultipleApplications");
		exclusiveApplication = properties.getProperty("db_ExclusiveApplication");
		extendedNamedInsured = properties.getProperty("db_ExtendedNamedInsured");
		yearIncepted = properties.getProperty("db_YearIncepted");
		formType = properties.getProperty("db_FormType");

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(insured);
		polBuild = new QueryBuilder(connection);
		polBuild.tableName("Policy");
	}

	public Map<String, Object> getInsuredDetails(String policyNumber, int transactionId) {
		List<String> outFields = new ArrayList<>();
		outFields.add(policyInsuredId);
		outFields.add(hvn);
		outFields.add(product);
		outFields.add(agentId);
		outFields.add(name);
		outFields.add(address1);
		outFields.add(address2);
		outFields.add(city);
		outFields.add(state);
		outFields.add(zip);
		outFields.add(country);
		outFields.add(prepared);
		outFields.add(agencyContact);
		outFields.add(renewalStatus);
		outFields.add(createDateTime);
		outFields.add(noMultipleApplications);
		outFields.add(exclusiveApplication);
		outFields.add(extendedNamedInsured);
		outFields.add(yearIncepted);
		outFields.add(formType);

		build.outFields(outFields);
		build.whereBy("transactionId" + " =" + transactionId);
		List<Map<String, Object>> insuredDetails = build.execute(60);
		return results = insuredDetails.get(0);
	}

	public Map<String, String> getInsuredDataExpected(String tcid) {
		List<Map<String, String>> binderInsuredData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"Insured").readExcelRowWise();

		for (Map<String, String> element : binderInsuredData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData = element;
				break;
			}
		}
		return TCData;

	}


	public String verifyInsuredDetails(Map<String, String> binderData, String policyNumber, int transactionId) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		//get the aftershock values
		insuredResults = getInsuredDetails(policyNumber, transactionId);

		//need to get policyInsuredId from the policy table
		String expectedPolInsuredId = build.fetch("PolicyInsuredId", "Policy","TransactionId = " + transactionId).toString();
		testStatus = Assertions.verify(insuredResults.get("PolicyInsuredId").toString(), expectedPolInsuredId, "PolicyInsuredId : " + insuredResults.get("PolicyInsuredId"), "Expected : " + expectedPolInsuredId, false, false);
		testStatus = Assertions.verify(insuredResults.get("HVN").toString(), binderData.get("HVN").toString(), "HVN : " + insuredResults.get("HVN"), "Expected : " + binderData.get("HVN").toString(), false, false);
		testStatus = Assertions.verify(insuredResults.get("Product"), binderData.get("Product"), "Product : " + insuredResults.get("Product"), "Expected : " + binderData.get("Product"), false, false);
		String accountName = ExcelReportUtil.getInstance().getLatestInsuredNameFromMaster(binderData.get("TCID"));
		testStatus = Assertions.verify(insuredResults.get("Name"), accountName, "Name : " + insuredResults.get("Name"), "Expected : " + accountName, false, false);
		testStatus = Assertions.verify(insuredResults.get("Address1"), binderData.get("Address1"), "Address1 : " + insuredResults.get("Address1"), "Expected : " + binderData.get("Address1"), false, false);
		testStatus = Assertions.verify(insuredResults.get("Address2").toString().trim(), binderData.get("Address2").toString().trim(), "Address2 : " + insuredResults.get("Address2").toString().trim(), "Expected : " + binderData.get("Address2").toString().trim(), false, false);
		testStatus = Assertions.verify(insuredResults.get("City"), binderData.get("City"), "City : " + insuredResults.get("City"), "Expected : " + binderData.get("City"), false, false);
		testStatus = Assertions.verify(insuredResults.get("State").toString().replace(" ", ""), binderData.get("State").toString().replace(" ", ""), "State : " + insuredResults.get("State").toString().replace(" ", ""), "Expected : " + binderData.get("State").toString(), false, false);
		testStatus = Assertions.verify(insuredResults.get("Zip"), binderData.get("Zip"), "Zip : " + insuredResults.get("Zip"), "Expected : " + binderData.get("Zip"), false, false);
		testStatus = Assertions.verify(insuredResults.get("Country"), binderData.get("Country"), "Country : " + insuredResults.get("Country"), "Expected : " + binderData.get("Country"), false, false);
		testStatus = Assertions.verify(insuredResults.get("Prepared").toString(), binderData.get("Prepared").toString(), "Prepared : " + insuredResults.get("Prepared").toString(), "Expected : " + binderData.get("Prepared").toString(), false, false);
		testStatus = Assertions.verify(insuredResults.get("AgencyContact"), binderData.get("AgencyContact"), "AgencyContact : " + insuredResults.get("AgencyContact"), "Expected : " + binderData.get("AgencyContact"), false, false);
		testStatus = Assertions.verify(insuredResults.get("RenewalStatus"), binderData.get("RenewalStatus"), "RenewalStatus : " + insuredResults.get("RenewalStatus"), "Expected : " + binderData.get("RenewalStatus"), false, false);
		//using epicenter processing date
		String epiProcessDate = ExcelReportUtil.getInstance().getLatestProcessingDateFromMaster(binderData.get("TCID"));
		LocalDate asCreateDateTime = LocalDate.parse(String.valueOf(insuredResults.get("CreateDateTime")).substring(0,10));
		testStatus = Assertions.verify(asCreateDateTime.format(f), epiProcessDate, "CreateDateTime : " + asCreateDateTime.format(f), "Expected : " + epiProcessDate, false, false);
		testStatus = Assertions.verify(insuredResults.get("NoMultipleApplications"), binderData.get("NoMultipleApplications"), "NoMultipleApplications : " + insuredResults.get("NoMultipleApplications"), "Expected : " + binderData.get("NoMultipleApplications"), false, false);
		testStatus = Assertions.verify(insuredResults.get("ExclusiveApplication"), binderData.get("ExclusiveApplication"), "ExclusiveApplication : " + insuredResults.get("ExclusiveApplication"), "Expected : " + binderData.get("ExclusiveApplication"), false, false);
		testStatus = Assertions.verify(insuredResults.get("ExtendedNamedInsured").toString().trim(), binderData.get("ExtendedNamedInsured").trim(), "ExtendedNamedInsured : " + insuredResults.get("ExtendedNamedInsured"), "Expected : " + binderData.get("ExtendedNamedInsured"), false, false);
		testStatus = Assertions.verify(insuredResults.get("YearIncepted").toString(), binderData.get("YearIncepted").toString(), "YearIncepted : " + insuredResults.get("YearIncepted").toString(), "Expected : " + binderData.get("YearIncepted").toString(), false, false);
		testStatus = Assertions.verify(insuredResults.get("FormType").toString(), binderData.get("FormType").toString(), "FormType : " + insuredResults.get("FormType").toString(), "Expected : " + binderData.get("FormType").toString(), false, false);



		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}





}
